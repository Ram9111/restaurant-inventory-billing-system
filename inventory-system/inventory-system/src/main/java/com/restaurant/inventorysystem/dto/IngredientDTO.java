package com.restaurant.inventorysystem.dto;

import java.math.BigDecimal;
/**
 * IngredientDTO
 * ------------------------------------------------------------
 * @author  Ram Choudhary
 * @date    10-Nov-2025
 * @purpose Data Transfer Object (DTO) for transferring ingredient
 *          details between layers (Entity ↔ Controller/Frontend)
 *          without exposing entity structure directly.
 * ------------------------------------------------------------
 */
public class IngredientDTO {
    private Integer ingredientId;
    private String ingredientName;
    private String ingredientCode;
    private String ingredientDescription;
    private String uom;


    private BigDecimal currentStockSubunit;


    private String smallerUnit;
    private BigDecimal baseUnitValue = BigDecimal.ONE;
    private int activeFlag;
    private int enableFlag;
    private Integer modifiedBy;

    //   Constructors
    public IngredientDTO() {}

    public IngredientDTO(com.restaurant.inventorysystem.entity.IngredientsMaster ingredient) {
        this.ingredientId = ingredient.getIngredientId();
        this.ingredientName = ingredient.getIngredientName();
        this.ingredientCode = ingredient.getIngredientCode();
        this.ingredientDescription = ingredient.getIngredientDescription();
        this.uom = ingredient.getUom();
        this.smallerUnit = ingredient.getSmallerUnit();
        this.baseUnitValue = ingredient.getBaseUnitValue();
        this.activeFlag = ingredient.getActiveFlag();
        this.enableFlag = ingredient.getEnableFlag();
        this.modifiedBy = ingredient.getUpdatedBy() != null
                ? ingredient.getUpdatedBy().getUserId()
                : null;
        this.currentStockSubunit = ingredient.getCurrentStockSubunit();
    }

    //   Getters and Setters
    public Integer getIngredientId() { return ingredientId; }
    public void setIngredientId(Integer ingredientId) { this.ingredientId = ingredientId; }

    public String getIngredientName() { return ingredientName; }
    public void setIngredientName(String ingredientName) { this.ingredientName = ingredientName; }

    public String getIngredientCode() { return ingredientCode; }
    public void setIngredientCode(String ingredientCode) { this.ingredientCode = ingredientCode; }

    public String getIngredientDescription() { return ingredientDescription; }
    public void setIngredientDescription(String ingredientDescription) { this.ingredientDescription = ingredientDescription; }

    public String getUom() { return uom; }
    public void setUom(String uom) { this.uom = uom; }

    public int getActiveFlag() { return activeFlag; }
    public void setActiveFlag(int activeFlag) { this.activeFlag = activeFlag; }

    public int getEnableFlag() { return enableFlag; }
    public void setEnableFlag(int enableFlag) { this.enableFlag = enableFlag; }

    public Integer getModifiedBy() { return modifiedBy; }
    public void setModifiedBy(Integer modifiedBy) { this.modifiedBy = modifiedBy; }
    public String getSmallerUnit() {
        return smallerUnit;
    }

    public void setSmallerUnit(String smallerUnit) {
        this.smallerUnit = smallerUnit;
    }

    public BigDecimal getBaseUnitValue() {
        return baseUnitValue;
    }

    public void setBaseUnitValue(BigDecimal baseUnitValue) {
        this.baseUnitValue = baseUnitValue;
    }
    public BigDecimal getCurrentStockSubunit() {
        return currentStockSubunit;
    }

    public void setCurrentStockSubunit(BigDecimal currentStockSubunit) {
        this.currentStockSubunit = currentStockSubunit;
    }

}
