package com.restaurant.inventorysystem.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;
/**
 * Data Transfer Object (DTO) used for sending Stock-In details
 * from the backend service layer to the frontend (UI or API response).
 *
 * This class contains complete stock entry information, including
 * quantities, cost details, supplier info, and audit metadata.
 *
 * @author
 * @since 2025
 */
public class StockInResponseDTO {

    private Integer stockInId;
    private Integer ingredientId;
    private String ingredientName;

    private String stockNo;
    private LocalDateTime stockInDate;

    private BigDecimal qtyMain;
    private BigDecimal qtySub;

    private BigDecimal previousStockSub;
    private BigDecimal updatedStockSub;

    private BigDecimal costPerUnit;
    private BigDecimal totalCost;

    private String supplierName;
    private String remarks;

    private Integer createdBy;
    private LocalDateTime createdDate;

    /** Getters and Setters */
    public Integer getStockInId() {
        return stockInId;
    }

    public void setStockInId(Integer stockInId) {
        this.stockInId = stockInId;
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getStockNo() {
        return stockNo;
    }

    public void setStockNo(String stockNo) {
        this.stockNo = stockNo;
    }

    public LocalDateTime getStockInDate() {
        return stockInDate;
    }

    public void setStockInDate(LocalDateTime stockInDate) {
        this.stockInDate = stockInDate;
    }

    public BigDecimal getQtyMain() {
        return qtyMain;
    }

    public void setQtyMain(BigDecimal qtyMain) {
        this.qtyMain = qtyMain;
    }

    public BigDecimal getQtySub() {
        return qtySub;
    }

    public void setQtySub(BigDecimal qtySub) {
        this.qtySub = qtySub;
    }

    public BigDecimal getPreviousStockSub() {
        return previousStockSub;
    }

    public void setPreviousStockSub(BigDecimal previousStockSub) {
        this.previousStockSub = previousStockSub;
    }

    public BigDecimal getUpdatedStockSub() {
        return updatedStockSub;
    }

    public void setUpdatedStockSub(BigDecimal updatedStockSub) {
        this.updatedStockSub = updatedStockSub;
    }

    public BigDecimal getCostPerUnit() {
        return costPerUnit;
    }

    public void setCostPerUnit(BigDecimal costPerUnit) {
        this.costPerUnit = costPerUnit;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
