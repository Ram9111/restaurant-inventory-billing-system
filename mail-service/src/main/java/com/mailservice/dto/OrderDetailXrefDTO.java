package com.mailservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * ------------------------------------------------------------
 * DTO: OrderDetailXrefDTO
 * ------------------------------------------------------------
 * Purpose:
 *  Represents a single recipe/item within an order.
 *
 * Author : Ram Choudhary
 * Date   : 12-Nov-2025
 */
public class OrderDetailXrefDTO {

    private Integer orderDetailXrefId;
    private Integer orderDetailId;
    private Integer recipeId;
    private String recipeName;
    private BigDecimal quantity;
    private BigDecimal sellingPrice;

    private Integer createdBy;
    private Integer modifiedBy;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Integer activeFlag;
    private Integer enableFlag;

    // ---------- Getters and Setters ----------

    public Integer getOrderDetailXrefId() {
        return orderDetailXrefId;
    }

    public void setOrderDetailXrefId(Integer orderDetailXrefId) {
        this.orderDetailXrefId = orderDetailXrefId;
    }

    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Integer activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Integer getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(Integer enableFlag) {
        this.enableFlag = enableFlag;
    }
}
