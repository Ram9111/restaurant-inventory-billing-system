package com.restaurant.inventorysystem.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * StockInRequestDTO
 * ------------------------------------------------------------
 * @author  Ram Choudhary
 * @date    11-Nov-2025
 * @purpose DTO (Data Transfer Object) used to receive stock-in
 *          details from the UI or API request. This class carries
 *          data related to adding new ingredient stock to the
 *          inventory system.
 *
 *          It includes information such as ingredient ID, quantity,
 *          cost, supplier details, remarks, and created user info.
 * ------------------------------------------------------------
 */

public class StockInRequestDTO {

    private Integer ingredientId;
    private BigDecimal qtyMain;          // main quantity (kg or L or pcs)
    private BigDecimal costPerUnit;
    private String supplierName;
    private String remarks;
    private Integer createdBy;

    private LocalDateTime stockInDate;   // optional from UI

    //   Getters + Setters

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public BigDecimal getQtyMain() {
        return qtyMain;
    }

    public void setQtyMain(BigDecimal qtyMain) {
        this.qtyMain = qtyMain;
    }

    public BigDecimal getCostPerUnit() {
        return costPerUnit;
    }

    public void setCostPerUnit(BigDecimal costPerUnit) {
        this.costPerUnit = costPerUnit;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getStockInDate() {
        return stockInDate;
    }

    public void setStockInDate(LocalDateTime stockInDate) {
        this.stockInDate = stockInDate;
    }
}
