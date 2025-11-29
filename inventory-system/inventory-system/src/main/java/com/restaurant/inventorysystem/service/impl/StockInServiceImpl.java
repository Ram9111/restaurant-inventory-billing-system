package com.restaurant.inventorysystem.service.impl;

import com.restaurant.inventorysystem.dto.StockInResponseDTO;
import com.restaurant.inventorysystem.dto.StockReportDTO;
import com.restaurant.inventorysystem.entity.IngredientsMaster;
import com.restaurant.inventorysystem.entity.StockIn;
import com.restaurant.inventorysystem.entity.User;
import com.restaurant.inventorysystem.exception.InvalidDataException;
import com.restaurant.inventorysystem.exception.ResourceNotFoundException;
import com.restaurant.inventorysystem.dto.StockInRequestDTO;
import com.restaurant.inventorysystem.repository.IngredientsRepository;
import com.restaurant.inventorysystem.repository.StockInRepository;
import com.restaurant.inventorysystem.service.StockInService;
import com.restaurant.inventorysystem.util.ExcelExportUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation class for Stock In service.
 * Contains full business logic for adding stock,
 * converting quantities, updating ingredient stock,
 * fetching records and deleting entries.
 *
 * @author Ram
 * @date 2025-11-08
 * @purpose Business logic for Stock-In module
 */
@Service
public class StockInServiceImpl implements StockInService {

    @Autowired
    private StockInRepository stockInRepository;

    @Autowired
    private IngredientsRepository ingredientsRepository;


    /**
     * Adds stock for a given ingredient. This method performs:
     * 1. Fetching ingredient from DB
     * 2. Converting main quantity (e.g., KG/LTR) into subunit quantity (grams/ml)
     * 3. Updating the ingredient's current stock
     * 4. Creating and saving a StockIn entry
     * 5. Returning a clean DTO response
     *
     * This ensures accurate inventory management and prevents stock mismatch.
     *
     * @author Ram
     * @date 2025-11-09
     * @purpose Add a new stock-in entry and update ingredient stock accordingly
     *
     * @param req StockInRequestDTO containing ingredientId, qtyMain, supplierName, costPerUnit,
     *            remarks, stockInDate, createdBy
     *
     * @return StockInResponseDTO containing stock details, updated inventory values,
     *         and linked ingredient info
     */
    @Override
    public StockInResponseDTO addStock(StockInRequestDTO req) {

        //   Step 1: Fetch ingredient
        IngredientsMaster ingredient = ingredientsRepository.findById(req.getIngredientId())
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with ID: " + req.getIngredientId()));

        //   Step 2: Convert qtyMain → qtySub (main unit to smallest unit)
        BigDecimal qtySub = req.getQtyMain().multiply(ingredient.getBaseUnitValue());

        //   Step 3: Compute previous + updated stock
        BigDecimal previousStock = ingredient.getCurrentStockSubunit();
        BigDecimal updatedStock = previousStock.add(qtySub);

        //   Step 4: Update ingredient master with new stock
        ingredient.setCurrentStockSubunit(updatedStock);
        ingredient.setUpdatedDate(LocalDateTime.now());
        ingredientsRepository.save(ingredient);

        //   Step 5: Prepare StockIn entry
        StockIn stock = new StockIn();
        stock.setIngredient(ingredient);
        stock.setStockNo(generateStockNo());
        stock.setStockInDate(req.getStockInDate() != null ? req.getStockInDate() : LocalDateTime.now());
        stock.setSupplierName(req.getSupplierName());
        stock.setQtyMain(req.getQtyMain());
        stock.setQtySub(qtySub);
        stock.setPreviousStockSub(previousStock);
        stock.setUpdatedStockSub(updatedStock);

        //   Cost calculations
        stock.setCostPerUnit(req.getCostPerUnit());
        stock.setTotalCost(
                req.getCostPerUnit() != null
                        ? req.getCostPerUnit().multiply(req.getQtyMain())
                        : null
        );

        //   Save createdBy as User foreign key
        User createdUser = new User();
        createdUser.setUserId(req.getCreatedBy());
        stock.setCreatedBy(createdUser);

        stock.setCreatedDate(LocalDateTime.now());
        stock.setRemarks(req.getRemarks());

        //   Save stock entry in DB
        StockIn saved = stockInRepository.save(stock);

        //   Convert and return DTO
        return mapToDTO(saved);
    }


    /**
     * Converts a StockIn entity into a StockInResponseDTO.
     * <p>
     * This method removes all Hibernate lazy-loaded objects,
     * prevents serialization issues (HibernateProxy, ByteBuddy),
     * and returns clean, UI-friendly JSON.
     * <p>
     * Mapping is essential to avoid circular dependencies and
     * ensure only required fields are exposed to the frontend.
     *
     * @author Ram
     * @date 2025-11-09
     * @purpose Convert StockIn entity to a safe DTO for API response
     *
     * @param stock The StockIn entity fetched from the database
     *
     * @return StockInResponseDTO containing mapped stock details,
     *         ingredient info, stock calculations, and created metadata
     */
    private StockInResponseDTO mapToDTO(StockIn stock) {

        StockInResponseDTO dto = new StockInResponseDTO();

        //   Basic stock information
        dto.setStockInId(stock.getStockInId());
        dto.setStockNo(stock.getStockNo());
        dto.setStockInDate(stock.getStockInDate());

        //   Ingredient details (mapped from ManyToOne relation)
        dto.setIngredientId(stock.getIngredient().getIngredientId());
        dto.setIngredientName(stock.getIngredient().getIngredientName());

        //   Quantity details (main & sub unit)
        dto.setQtyMain(stock.getQtyMain());
        dto.setQtySub(stock.getQtySub());

        //   Stock before & after entry
        dto.setPreviousStockSub(stock.getPreviousStockSub());
        dto.setUpdatedStockSub(stock.getUpdatedStockSub());

        //   Cost information
        dto.setCostPerUnit(stock.getCostPerUnit());
        dto.setTotalCost(stock.getTotalCost());

        //   Additional stock metadata
        dto.setSupplierName(stock.getSupplierName());
        dto.setRemarks(stock.getRemarks());

        //   Created metadata
        dto.setCreatedBy(stock.getCreatedBy().getUserId());
        dto.setCreatedDate(stock.getCreatedDate());

        return dto;
    }



    /**
     * Retrieves all Stock-In records from the database.
     * <p>
     * This method:
     * 1. Fetches all StockIn entities
     * 2. Converts each entity into a StockInResponseDTO
     * 3. Returns a clean, UI-friendly list without Hibernate lazy-loading issues
     * <p>
     * DTO mapping ensures safe JSON serialization and prevents exposing
     * internal entity structure to the frontend.
     *
     * @author Ram
     * @date 2025-11-09
     * @purpose Fetch all Stock-In entries and return them as DTO list
     *
     * @return List<StockInResponseDTO> containing stock-in data for UI display
     */
    @Override
    public List<StockInResponseDTO> getAllStockIn() {

        return stockInRepository.findAll()
                .stream()
                .map(this::mapToDTO)     //   convert entity to DTO
                .toList();
    }




    /**
     * Retrieves a single Stock-In record by its ID.
     * <p>
     * Steps performed:
     * 1. Fetch StockIn entity from the database
     * 2. If not found → throw ResourceNotFoundException
     * 3. Convert the entity to StockInResponseDTO
     * 4. Return clean, UI-safe DTO without lazy loading issues
     * <p>
     * DTO conversion prevents HibernateProxy serialization errors and ensures
     * that only required data is returned to the frontend.
     *
     * @author Ram
     * @date 2025-11-09
     * @purpose Fetch a specific Stock-In entry by ID and map it to a DTO
     *
     * @param id The Stock-In entry ID to retrieve
     *
     * @return StockInResponseDTO containing detailed stock information
     */
    @Override
    public StockInResponseDTO getStockInById(Integer id) {

        //   Step 1: Fetch entity
        StockIn stock = stockInRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock-In not found with ID: " + id));

        //   Step 2: Convert entity → DTO
        return mapToDTO(stock);
    }



    /**
     * Deletes (soft delete) a Stock-In entry by ID.
     * <p>
     * This method performs several critical inventory operations:
     * <ol>
     *     <li>Fetch the Stock-In entry from the database</li>
     *     <li>Fetch the linked Ingredient Master record</li>
     *     <li>Check if reversing the stock will cause negative inventory</li>
     *     <li>Subtract the Stock-In quantity from Ingredient Master stock</li>
     *     <li>Update the ingredient's current stock</li>
     *     <li>Soft delete the Stock-In entry (activeFlag & enableFlag set to 0)</li>
     * </ol>
     * <p>
     * Validation ensures inventory is never allowed to become negative,
     * maintaining ERP stock integrity. If not enough stock exists to reverse,
     * an InvalidDataException is thrown.
     *
     * @author Ram
     * @date 2025-11-09
     * @purpose Reverse a stock-in entry and update ingredient stock safely
     *
     * @param id The ID of the Stock-In entry to delete
     *
     * @throws ResourceNotFoundException If the stock entry is not found
     * @throws InvalidDataException If reversing the stock causes negative stock
     */
    @Override
    public void deleteStockIn(Integer id) {

        //  Step 1: Get stock entry
        StockIn stock = stockInRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock-In not found with ID: " + id));

        //  Step 2: Get ingredient linked with the stock
        IngredientsMaster ingredient = stock.getIngredient();

        //  Step 3: Check if this stock can be reversed (subtract from ingredient stock)
        BigDecimal currentStock = ingredient.getCurrentStockSubunit();     // total current stock (subunit)
        BigDecimal qtyToSubtract = stock.getQtySub();                      // qty that was added earlier

        //  Step 4: Validate stock before subtraction
        if (currentStock.compareTo(qtyToSubtract) < 0) {
            throw new InvalidDataException(
                    "Cannot delete stock entry. Not enough stock available to subtract in Ingredient Master. " +
                            "Available: " + currentStock + ", Required: " + qtyToSubtract
            );
        }

        //  Step 5: Reverse the stock (subtract)
        BigDecimal updatedStock = currentStock.subtract(qtyToSubtract);
        ingredient.setCurrentStockSubunit(updatedStock);
        ingredient.setUpdatedDate(LocalDateTime.now());

        //  Update ingredient record
        ingredientsRepository.save(ingredient);

        //  Step 6: Soft delete the stock-in record
        stock.setActiveFlag(0);
        stock.setEnableFlag(0);
        stock.setModifiedDate(LocalDateTime.now());

        stockInRepository.save(stock);
    }



    //   Generate unique stock number
    private String generateStockNo() {
        return "STK-" + System.currentTimeMillis();
    }




    /**
     * ------------------------------------------------------------
     * STOCK REPORT SERVICE IMPLEMENTATION
     * ------------------------------------------------------------
     * Purpose:
     *  - Calculate current stock (main + subunit)
     *  - Generate Stock Report in JSON format
     *  - Export Stock Report to Excel (.xlsx)
     *
     * Author  : Ram Choudhary
     * Created : 12-Nov-2025
     *
     * Notes:
     *  Stock calculation is based on IngredientsMaster table:
     *      currentStockSubunit / baseUnitValue = stockMain
     *
     *  Example:
     *      50000 grams / 1000 = 50 KG
     */
    @Override
    public List<StockReportDTO> getStockReport() {

        // Fetch all active & enabled ingredients
        List<IngredientsMaster> ingredients = ingredientsRepository.findAllActiveIngredients();

        // Convert Entity → DTO using Java Stream
        return ingredients.stream().map(i -> {

            StockReportDTO dto = new StockReportDTO();
            dto.setIngredientId(i.getIngredientId());
            dto.setIngredientName(i.getIngredientName());
            dto.setUom(i.getUom());
            dto.setBaseUnitValue(i.getBaseUnitValue());

            // Stock stored in subunits (gram/ml)
            dto.setStockSubunit(i.getCurrentStockSubunit());

            // Convert subunits → main unit (gram → KG)
            BigDecimal main = BigDecimal.ZERO;
            if (i.getBaseUnitValue() != null && i.getBaseUnitValue().compareTo(BigDecimal.ZERO) > 0) {
                main = i.getCurrentStockSubunit().divide(i.getBaseUnitValue());
            }
            dto.setStockMain(main);

            // Last updated timestamp
            dto.setLastUpdatedDate(i.getUpdatedDate());

            return dto;

        }).toList();
    }


    /**
     * ------------------------------------------------------------
     * EXPORT STOCK REPORT TO EXCEL (.xlsx)
     * ------------------------------------------------------------
     * Purpose:
     *  Generates Excel file with current stock summary.
     *
     * Steps:
     *  1. Fetch Stock Report List
     *  2. Create workbook + sheet
     *  3. Add headers (bold)
     *  4. Add data rows
     *  5. Apply date formatting
     *  6. Auto-size columns
     *  7. Stream file to response (browser/Postman download)
     *
     * File Name:
     *  stock_report.xlsx
     */

    @Override
    public void exportStockReportToExcel(HttpServletResponse response) throws Exception {

        List<StockReportDTO> list = getStockReport();

        List<String> headers = Arrays.asList(
                "Ingredient", "UOM", "Base Unit",
                "Stock (Subunit)", "Stock (Main)", "Last Updated"
        );

        List<List<Object>> rows = list.stream()
                .map(dto -> Arrays.asList(
                        (Object) dto.getIngredientName(),
                        dto.getUom(),
                        dto.getBaseUnitValue(),
                        dto.getStockSubunit(),
                        dto.getStockMain(),
                        dto.getLastUpdatedDate()
                ))
                .toList();

        ExcelExportUtil.generateExcel(response, "Stock Report", headers, rows);
    }


}
