package com.restaurant.inventorysystem.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.inventorysystem.dto.OrderDetailDTO;
import com.restaurant.inventorysystem.dto.OrderDetailXrefDTO;
import com.restaurant.inventorysystem.entity.*;
import com.restaurant.inventorysystem.exception.ResourceNotFoundException;
import com.restaurant.inventorysystem.kafka.KafkaInvoiceProducer;
import com.restaurant.inventorysystem.repository.*;
import com.restaurant.inventorysystem.service.OrderDetailService;
import com.restaurant.inventorysystem.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;
import java.util.HashMap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ------------------------------------------------------------
 * SERVICE IMPLEMENTATION: Order Detail
 * ------------------------------------------------------------
 * Handles order saving, stock validation, and deduction.
 *
 * Flow:
 *  1️⃣ Save OrderDetail
 *  2️⃣ Save OrderDetailXref list
 *  3️⃣ Validate and reduce Ingredients stock
 *
 * Author : Ram Choudhary
 * Date   : 12-Nov-2025
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderDetailXrefRepository orderDetailXrefRepository;

    @Autowired
    private RecipeMasterRepository recipeMasterRepository;

    @Autowired
    private RecipeIngredientXrefRepository recipeIngredientXrefRepository;

    @Autowired
    private IngredientsRepository ingredientsRepository;

    @Autowired
    private  UserRepository userRepo;

    @Autowired
    private KafkaInvoiceProducer kafkaInvoiceProducer;

    @Autowired
    private ObjectMapper objectMapper; // Add this

    /**
     * Author  : Ram Choudhary
     * Date    : 13-Nov-2025
     * Purpose : To save a new customer order along with its recipe items (xref)
     *            and update the stock levels of ingredients used in each recipe.
     *
     * Description:
     *  - Saves the main OrderDetail entry (customer, totals, etc.).
     *  - Saves all related OrderDetailXref entries (recipe-level details).
     *  - Deducts ingredient stock from IngredientsMaster table
     *    based on recipe compositions defined in RecipeIngredientXref.
     *
     * Transactional Behavior:
     *  - Ensures atomicity; if any step fails (e.g., stock shortage),
     *    the entire transaction rolls back to maintain data integrity.
     */
    @Override
    @Transactional
    public ApiResponse saveOrder(OrderDetailDTO orderDetailDTO) {

        // Step 1️⃣: Save main order detail
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderNo(orderDetailDTO.getOrderNo());
        orderDetail.setTableNo(orderDetailDTO.getTableNo());
        orderDetail.setOrderType(orderDetailDTO.getOrderType());
        orderDetail.setCustomerName(orderDetailDTO.getCustomerName());
        orderDetail.setCustomerPhone(orderDetailDTO.getCustomerPhone());
        orderDetail.setOrderDate(orderDetailDTO.getOrderDate());
        orderDetail.setTotalAmount(orderDetailDTO.getTotalAmount());
        orderDetail.setDiscount(orderDetailDTO.getDiscount());
        orderDetail.setTaxAmount(orderDetailDTO.getTaxAmount());
        orderDetail.setGrandTotal(orderDetailDTO.getGrandTotal());
        orderDetail.setPaymentMode(orderDetailDTO.getPaymentMode());
        orderDetail.setRemark(orderDetailDTO.getRemark());
        if (orderDetailDTO.getCreatedBy() != null) {
            orderDetail.setCreatedBy(
                    userRepo.findById(orderDetailDTO.getCreatedBy())
                            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + orderDetailDTO.getCreatedBy()))
            );
        }

        if (orderDetailDTO.getModifiedBy() != null) {
            orderDetail.setModifiedBy(
                    userRepo.findById(orderDetailDTO.getModifiedBy())
                            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + orderDetailDTO.getModifiedBy()))
            );
        }
        //orderDetail.setCreatedBy(orderDetailDTO.getCreatedBy());
        //orderDetail.setModifiedBy(orderDetailDTO.getModifiedBy());
        orderDetail.setActiveFlag(1);
        orderDetail.setEnableFlag(1);

        orderDetail = orderDetailRepository.save(orderDetail);

        // Step 2️⃣: Save each recipe item (xref)
        for (OrderDetailXrefDTO xrefDTO : orderDetailDTO.getOrderDetailXrefList()) {

            RecipeMaster recipe = recipeMasterRepository.findById(xrefDTO.getRecipeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Recipe not found: " + xrefDTO.getRecipeId()));

            OrderDetailXref xref = new OrderDetailXref();
            xref.setOrderDetail(orderDetail);
            xref.setRecipeMaster(recipe);
            xref.setRecipeName(recipe.getRecipeName());
            xref.setQuantity(xrefDTO.getQuantity());
            xref.setSellingPrice(recipe.getSellingPrice());
            if (orderDetailDTO.getCreatedBy() != null) {
                xref.setCreatedBy(
                        userRepo.findById(orderDetailDTO.getCreatedBy())
                                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + orderDetailDTO.getCreatedBy()))
                );
            }

            if (orderDetailDTO.getModifiedBy() != null) {
                xref.setModifiedBy(
                        userRepo.findById(orderDetailDTO.getModifiedBy())
                                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + orderDetailDTO.getModifiedBy()))
                );
            }
//            xref.setCreatedBy(orderDetailDTO.getCreatedBy());
//            xref.setModifiedBy(orderDetailDTO.getModifiedBy());
            xref.setActiveFlag(1);
            xref.setEnableFlag(1);

            orderDetailXrefRepository.save(xref);

            // Step 3️⃣: Fetch recipe ingredients and reduce stock
            List<RecipeIngredientXref> ingredientsUsed =
                    recipeIngredientXrefRepository.findByRecipe(recipe);

            for (RecipeIngredientXref ingredientXref : ingredientsUsed) {

                IngredientsMaster ingredient = ingredientXref.getIngredient();

                // Calculate required stock in subunits
                BigDecimal qtyNeeded = ingredientXref.getQuantityValue()
                        .multiply(xrefDTO.getQuantity());


                if (ingredient.getCurrentStockSubunit().compareTo(qtyNeeded) < 0) {
                    throw new ResourceNotFoundException(
                            "Insufficient stock for ingredient: " + ingredient.getIngredientName()
                                    + " | Available: " + ingredient.getCurrentStockSubunit()
                                    + " | Needed: " + qtyNeeded);
                }

                // Subtract stock from ingredient Master
                ingredient.setCurrentStockSubunit(ingredient.getCurrentStockSubunit().subtract(qtyNeeded));
                ingredientsRepository.save(ingredient);
            }
        }
        // ========== Step 4️⃣: Send Invoice Event to Kafka ==========
        try {

            OrderDetailDTO invoiceEvent = new OrderDetailDTO();

            invoiceEvent.setOrderId(orderDetail.getOrderId());
            invoiceEvent.setOrderNo(orderDetail.getOrderNo());
            invoiceEvent.setCustomerName(orderDetail.getCustomerName());
            invoiceEvent.setCustomerPhone(orderDetail.getCustomerPhone());
            invoiceEvent.setGrandTotal(orderDetail.getGrandTotal());
            invoiceEvent.setOrderDate(orderDetail.getOrderDate());
            invoiceEvent.setPaymentMode(orderDetail.getPaymentMode());
            invoiceEvent.setDiscount(orderDetail.getDiscount());
            invoiceEvent.setTaxAmount(orderDetail.getTaxAmount());
            invoiceEvent.setTotalAmount(orderDetail.getTotalAmount());
            invoiceEvent.setOrderDetailXrefList(orderDetailDTO.getOrderDetailXrefList());
            //Temp use mobile no. in place of email
            invoiceEvent.setCustomerEmail(orderDetailDTO.getCustomerEmail());
            String invoiceJson = objectMapper.writeValueAsString(invoiceEvent);

            kafkaInvoiceProducer.sendInvoiceEvent(invoiceJson);

        } catch (Exception e) {
            System.out.println("Failed to send invoice event: " + e.getMessage());
        }

        return new ApiResponse(200, "Order saved successfully", orderDetail.getOrderId());


    }

    /**
     * Author  : Ram Choudhary
     * Date    : 13-Nov-2025
     * Purpose : Fetch a specific order by its unique ID,
     *           including all associated recipe (xref) details.
     *
     * Description:
     *  - Retrieves the OrderDetail entity from the database.
     *  - Converts the entity to OrderDetailDTO for frontend/API use.
     *  - Includes all related OrderDetailXref entries (order items).
     */
    @Override
    public ApiResponse getOrderById(Integer id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id));

        //   map entity to DTO (including xref)
        OrderDetailDTO dto = mapToDTO(orderDetail);

        return new ApiResponse(200, "Order fetched successfully", dto);
    }

    /**
     * Author  : Ram Choudhary
     * Date    : 13-Nov-2025
     * Purpose : Retrieve all customer orders with their associated recipe (xref) data.
     *
     * Description:
     *  - Fetches all orders from the OrderDetail table.
     *  - Converts each entity to a corresponding DTO using the mapToDTO() helper.
     *  - Returns a list of fully prepared OrderDetailDTO objects for frontend consumption.
     */

    @Override
    public ApiResponse getAllOrders() {
        List<OrderDetail> orderList = orderDetailRepository.findAll();

        if (orderList.isEmpty()) {
            return new ApiResponse(404, "No orders found", Collections.emptyList());
        }

        //   Map each order (including xref data) to DTO
        List<OrderDetailDTO> dtoList = orderList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new ApiResponse(200, "Orders fetched successfully", dtoList);
    }

    /**
     * Helper Method
     * Author  : Ram Choudhary
     * Date    : 13-Nov-2025
     * Purpose : Convert OrderDetail entity into OrderDetailDTO.
     *
     * Description:
     *  - Transfers basic order data (orderNo, totals, customer info, etc.).
     *  - Maps associated xref (order items) list using mapXrefToDTO().
     */
    private OrderDetailDTO mapToDTO(OrderDetail entity) {
        OrderDetailDTO dto = new OrderDetailDTO();
        dto.setOrderId(entity.getOrderId());
        dto.setOrderNo(entity.getOrderNo());
        dto.setTableNo(entity.getTableNo());
        dto.setOrderType(entity.getOrderType());
        dto.setOrderDate(entity.getOrderDate());
        dto.setCustomerName(entity.getCustomerName());
        dto.setCustomerPhone(entity.getCustomerPhone());
        dto.setDiscount(entity.getDiscount());
        dto.setGrandTotal(entity.getGrandTotal());
        dto.setCreatedDate(entity.getCreatedDate());

        //   map xref list
        if (entity.getOrderDetailXrefList() != null) {
            List<OrderDetailXrefDTO> xrefDTOList = entity.getOrderDetailXrefList()
                    .stream()
                    .map(this::mapXrefToDTO)
                    .collect(Collectors.toList());
            dto.setOrderDetailXrefList(xrefDTOList);
        }

        return dto;
    }

    /**
     * Helper Method
     * Author  : Ram Choudhary
     * Date    : 13-Nov-2025
     * Purpose : Convert OrderDetailXref entity (order item) into OrderDetailXrefDTO.
     *
     * Description:
     *  - Maps item-level information such as recipe name, quantity, and price.
     *  - Used within mapToDTO() when converting the main order entity.
     */
    private OrderDetailXrefDTO mapXrefToDTO(OrderDetailXref xref) {
        OrderDetailXrefDTO dto = new OrderDetailXrefDTO();
        dto.setOrderDetailXrefId(xref.getOrderDetailXrefId());
        dto.setOrderDetailId(xref.getOrderDetail().getOrderId());
        dto.setRecipeName(xref.getRecipeName());
        dto.setRecipeId(xref.getRecipeMaster().getRecipeId());
        dto.setQuantity(xref.getQuantity());
        dto.setSellingPrice(xref.getSellingPrice());
        return dto;
    }

    /**
     * Author  : Ram Choudhary
     * Date    : 13-Nov-2025
     * Purpose : Perform a soft delete on an existing order while reversing
     *            the ingredient stock used in that order.
     *
     * Description:
     *  - Finds the order and its related xref records.
     *  - Marks them as inactive instead of physically deleting them.
     *  - Reverses ingredient stock by adding back the quantities used
     *    in each recipe of the order.
     *
     * Transactional Behavior:
     *  - Ensures atomic rollback if any stock update or order update fails.
     */
    @Override
    public ApiResponse deleteOrder(Integer id) {
        //  Step 1: Find order
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id));

        //  Step 2: Mark order as inactive (soft delete)
        orderDetail.setActiveFlag(0);
        orderDetail.setEnableFlag(0);

        //  Step 3: Mark related xref records as inactive too
        List<OrderDetailXref> xrefs = orderDetailXrefRepository.findByOrderDetailOrderId(id);
//        for (OrderDetailXref xref : xrefs) {
//
//        }
        for (OrderDetailXref xref : xrefs) {
            xref.setActiveFlag(0);
            xref.setEnableFlag(0);
            Integer receipyId = xref.getRecipeMaster().getRecipeId();
            BigDecimal recipyOrderQty = xref.getQuantity();
            List<RecipeIngredientXref> recipyXref =  recipeIngredientXrefRepository.findByRecipe_RecipeId(receipyId);
            for (RecipeIngredientXref recipeIngredientXref : recipyXref) {
                BigDecimal qtyValueOfIngrent = recipeIngredientXref.getQuantityValue();
                BigDecimal qtyNeedToSubstract =  qtyValueOfIngrent.multiply(recipyOrderQty);
                IngredientsMaster ingredientsMaster= recipeIngredientXref.getIngredient();
                ingredientsMaster.setCurrentStockSubunit(ingredientsMaster.getCurrentStockSubunit().add(qtyNeedToSubstract));
                ingredientsRepository.save(ingredientsMaster);
            }

        }
        //  Step 4: Save both parent and children
        orderDetailXrefRepository.saveAll(xrefs);
        orderDetailRepository.save(orderDetail);

        //  Step 5: Return success
        return new ApiResponse(200, "Order soft deleted successfully", null);
    }
    public UserRepository getUserRepo() {
        return userRepo;
    }

    public void setUserRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public IngredientsRepository getIngredientsRepository() {
        return ingredientsRepository;
    }

    public void setIngredientsRepository(IngredientsRepository ingredientsRepository) {
        this.ingredientsRepository = ingredientsRepository;
    }

    public RecipeIngredientXrefRepository getRecipeIngredientXrefRepository() {
        return recipeIngredientXrefRepository;
    }

    public void setRecipeIngredientXrefRepository(RecipeIngredientXrefRepository recipeIngredientXrefRepository) {
        this.recipeIngredientXrefRepository = recipeIngredientXrefRepository;
    }

    public RecipeMasterRepository getRecipeMasterRepository() {
        return recipeMasterRepository;
    }

    public void setRecipeMasterRepository(RecipeMasterRepository recipeMasterRepository) {
        this.recipeMasterRepository = recipeMasterRepository;
    }

    public OrderDetailXrefRepository getOrderDetailXrefRepository() {
        return orderDetailXrefRepository;
    }

    public void setOrderDetailXrefRepository(OrderDetailXrefRepository orderDetailXrefRepository) {
        this.orderDetailXrefRepository = orderDetailXrefRepository;
    }

    public OrderDetailRepository getOrderDetailRepository() {
        return orderDetailRepository;
    }

    public void setOrderDetailRepository(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

}

