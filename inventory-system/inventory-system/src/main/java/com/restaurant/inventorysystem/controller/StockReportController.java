package com.restaurant.inventorysystem.controller;

import com.restaurant.inventorysystem.dto.StockReportDTO;
import com.restaurant.inventorysystem.service.StockInService;
import com.restaurant.inventorysystem.util.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ------------------------------------------------------------
 * STOCK REPORT CONTROLLER
 * ------------------------------------------------------------
 * Purpose:
 *  This controller exposes APIs related to stock reporting,
 *  including JSON report and Excel export functionality.
 *
 * Author  : Ram Choudhary
 * Created : 12-Nov-2025
 *
 * Description:
 *  - /report        → Returns JSON report of current stock.
 *  - /report/excel  → Downloads Excel file of stock report.
 *
 * Notes:
 *  This controller only handles request routing. The full
 *  business logic is inside StockInService.
 */
@RestController
@RequestMapping("/api/stock")
public class StockReportController {

    @Autowired
    private StockInService stockInService;

    /**
     * ------------------------------------------------------------
     * API: GET /api/stock/report
     * ------------------------------------------------------------
     * Purpose:
     *  Fetches current stock details in JSON format.
     *
     * Returns:
     *  - ingredientId, name, UOM, baseUnitValue
     *  - stockSubunit, stockMain, lastUpdatedDate
     *
     * Response Example:
     *  {
     *      "status": 200,
     *      "message": "Stock Report Loaded",
     *      "data": [...]
     *  }
     */
    @GetMapping("/report")
    public ResponseEntity<ApiResponse> getStockReport() {
        List<StockReportDTO> list = stockInService.getStockReport();
        return ResponseEntity.ok(new ApiResponse(200, "Stock Report Loaded", list));
    }

    /**
     * ------------------------------------------------------------
     * API: GET /api/stock/report/excel
     * ------------------------------------------------------------
     * Purpose:
     *  Generates and downloads Stock Report as an Excel (.xlsx) file.
     *
     * Behavior:
     *  - Sets correct content type
     *  - Auto-downloads Excel file via browser/Postman
     *
     * File Name:
     *  stock_report.xlsx
     *
     * Notes:
     *  - Response does NOT return JSON.
     *  - Excel file is streamed directly to client.
     */
    @GetMapping("/report/excel")
    public void exportStockExcel(HttpServletResponse response) throws Exception {
        stockInService.exportStockReportToExcel(response);
    }
}
