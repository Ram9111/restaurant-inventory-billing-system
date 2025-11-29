package com.restaurant.inventorysystem.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Author: Ram Choudhary
 * Date: 07-Nov-2025
 * Description: DTO for RecipeIngredientXref row.
 * Since: 07-Nov-2025
 */
public class RecipeIngredientXrefDTO {
    private Integer recipeXrefId;       // optional for updates
    private Integer ingredientId;       // FK id only (clean API)
    private String uom;
    private String smallerUnit;
    private BigDecimal baseUnitValue;
    private BigDecimal quantityValue;
    private String remarks;
    private Integer activeFlag;
    private Integer enableFlag;
    private Integer createdById;
    private LocalDateTime createdDate;
    private Integer modifiedById;
    private LocalDateTime modifiedDate;

    // Getters & Setters
    public Integer getRecipeXrefId() { return recipeXrefId; }
    public void setRecipeXrefId(Integer recipeXrefId) { this.recipeXrefId = recipeXrefId; }
    public Integer getIngredientId() { return ingredientId; }
    public void setIngredientId(Integer ingredientId) { this.ingredientId = ingredientId; }
    public String getUom() { return uom; }
    public void setUom(String uom) { this.uom = uom; }
    public String getSmallerUnit() { return smallerUnit; }
    public void setSmallerUnit(String smallerUnit) { this.smallerUnit = smallerUnit; }
    public BigDecimal getBaseUnitValue() { return baseUnitValue; }
    public void setBaseUnitValue(BigDecimal baseUnitValue) { this.baseUnitValue = baseUnitValue; }
    public BigDecimal getQuantityValue() { return quantityValue; }
    public void setQuantityValue(BigDecimal quantityValue) { this.quantityValue = quantityValue; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public Integer getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Integer activeFlag) { this.activeFlag = activeFlag; }
    public Integer getEnableFlag() { return enableFlag; }
    public void setEnableFlag(Integer enableFlag) { this.enableFlag = enableFlag; }
    public Integer getCreatedById() { return createdById; }
    public void setCreatedById(Integer createdById) { this.createdById = createdById; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    public Integer getModifiedById() { return modifiedById; }
    public void setModifiedById(Integer modifiedById) { this.modifiedById = modifiedById; }
    public LocalDateTime getModifiedDate() { return modifiedDate; }
    public void setModifiedDate(LocalDateTime modifiedDate) { this.modifiedDate = modifiedDate; }
}
