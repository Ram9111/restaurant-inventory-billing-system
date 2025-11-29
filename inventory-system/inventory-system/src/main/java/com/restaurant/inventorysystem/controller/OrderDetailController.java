package com.restaurant.inventorysystem.controller;

import com.restaurant.inventorysystem.dto.OrderDetailDTO;
import com.restaurant.inventorysystem.service.OrderDetailService;
import com.restaurant.inventorysystem.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @Autowired
    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    /**
     *   Save order with all recipes/items
     * ------------------------------------------------
     * Accepts OrderDetailDTO (which includes order info + recipe xref list)
     * Calls service layer to save master and child details.
     */
    @PostMapping("/save")
    public ApiResponse saveOrder(@RequestBody OrderDetailDTO orderDetailDTO) {
        return orderDetailService.saveOrder(orderDetailDTO);
    }

    /**
     *   Get single order by ID
     */
    @GetMapping("/get/{id}")
    public ApiResponse getOrderById(@PathVariable Integer id) {
        return orderDetailService.getOrderById(id);
    }
//
    /**
     *   List all orders
     */
    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getAllOrders() {
        ApiResponse response = orderDetailService.getAllOrders();
        return ResponseEntity.ok(response);
    }

    /**
     *   Delete order by ID
     */
    @DeleteMapping("/delete/{id}")
    public ApiResponse deleteOrder(@PathVariable Integer id) {
        return orderDetailService.deleteOrder(id);
    }
}
