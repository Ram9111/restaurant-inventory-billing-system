package com.restaurant.inventorysystem.service;

import com.restaurant.inventorysystem.dto.StockInResponseDTO;
import com.restaurant.inventorysystem.dto.StockReportDTO;
import com.restaurant.inventorysystem.entity.StockIn;
import com.restaurant.inventorysystem.dto.StockInRequestDTO;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * Service interface for handling Stock In operations.
 * Defines methods for adding stock, retrieving stock entries,
 * and deleting stock records.
 *
 * @author Ram
 * @date 2025-11-08
 * @purpose Business logic contract for Stock-In module
 */
public interface StockInService {

    /**
     * Adds new stock for an ingredient.
     *
     * @param request StockInRequest containing ingredientId, qtyMain, supplier, cost, createdBy, remarks
     * @return Saved StockIn object
     */
   // StockIn addStock(StockInRequestDTO request);
    StockInResponseDTO addStock(StockInRequestDTO request);

    /**
     * Returns list of all stock-in entries.
     *
     * @return List of StockIn
     */
    List<StockInResponseDTO> getAllStockIn();

    /**
     * Returns a single stock-in record by its ID.
     *
     * @param id Stock-In ID
     * @return StockIn object
     */
    StockInResponseDTO getStockInById(Integer id);

    /**
     * Deletes a stock-in record (soft delete recommended).
     *
     * @param id Stock-In ID
     */
    void deleteStockIn(Integer id);

    /**
     * Return the json response of excel report data.
     */
    List<StockReportDTO> getStockReport();
    /**
     * Generate the excel data in excel format
     * @param response
     */
    void exportStockReportToExcel(HttpServletResponse response) throws Exception;
}
