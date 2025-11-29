package com.restaurant.inventorysystem.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * Data Transfer Object (DTO) representing summarized stock report details.
 *
 * This class is used to display current stock levels of each ingredient,
 * including unit details, conversion rates, and last update timestamps.
 *
 * Typically used in reporting modules or for Excel export.
 *
 * @author Ram
 * @since 2025
 */
public class StockReportDTO {

    private Integer ingredientId;
    private String ingredientName;

    private String uom;
    private BigDecimal baseUnitValue;

    private BigDecimal stockSubunit;
    private BigDecimal stockMain;

    private LocalDateTime lastUpdatedDate;

    // -------------------- GETTERS & SETTERS --------------------

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

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public BigDecimal getBaseUnitValue() {
        return baseUnitValue;
    }

    public void setBaseUnitValue(BigDecimal baseUnitValue) {
        this.baseUnitValue = baseUnitValue;
    }

    public BigDecimal getStockSubunit() {
        return stockSubunit;
    }

    public void setStockSubunit(BigDecimal stockSubunit) {
        this.stockSubunit = stockSubunit;
    }

    public BigDecimal getStockMain() {
        return stockMain;
    }

    public void setStockMain(BigDecimal stockMain) {
        this.stockMain = stockMain;
    }

    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
