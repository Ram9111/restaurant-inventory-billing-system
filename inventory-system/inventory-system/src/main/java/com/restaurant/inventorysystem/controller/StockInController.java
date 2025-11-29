package com.restaurant.inventorysystem.controller;
import com.restaurant.inventorysystem.dto.StockInResponseDTO;
import com.restaurant.inventorysystem.entity.StockIn;
import com.restaurant.inventorysystem.util.ApiResponse;
import com.restaurant.inventorysystem.dto.StockInRequestDTO;
import com.restaurant.inventorysystem.service.StockInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing Stock In operations.
 * Handles incoming API requests for adding stock,
 * retrieving stock entries, and related operations.
 *
 * @author Ram
 * @date 2025-11-08
 * @purpose REST controller for Stock-In module
 */
@RestController
@RequestMapping("/api/stock-in")
@CrossOrigin("*")
public class StockInController {

    @Autowired
    private StockInService stockInService;

    /**
     * API to add stock for an ingredient.
     *
     * @param request StockInRequest (ingredientId, qtyMain, supplierName, cost, createdBy)
     * @return ApiResponse with saved StockIn object
     */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addStock(@RequestBody StockInRequestDTO request) {

        StockInResponseDTO response = stockInService.addStock(request);

        return ResponseEntity.ok(
                new ApiResponse(200, "Stock added successfully")
        );
    }



    /**
     * API to fetch all stock-in entries.
     * Useful for history/listing on UI.
     *
     * @return ApiResponse containing list of StockIn
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllStockIn() {
        List<StockInResponseDTO> response = stockInService.getAllStockIn();
        return ResponseEntity.ok(
                new ApiResponse(200, "Stock fetched successfully", response )
        );
    }


    /**
     * API to fetch a single stock-in entry by ID.
     *
     * @param id Stock-In ID
     * @return ApiResponse containing StockIn
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getStockInById(@PathVariable Integer id) {
        StockInResponseDTO response = stockInService.getStockInById(id);
        return ResponseEntity.ok(
                new ApiResponse(200, "Stock record fetched successfully",
                        response )
        );
    }


    /**
     * API to delete a stock-in entry (soft delete recommended with activeFlag).
     *
     * @param id Stock-In ID
     * @return ApiResponse with success message
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteStockIn(@PathVariable Integer id) {

        stockInService.deleteStockIn(id);

        return ResponseEntity.ok(
                new ApiResponse(200, "Stock record deleted successfully", null)
        );
    }
}

