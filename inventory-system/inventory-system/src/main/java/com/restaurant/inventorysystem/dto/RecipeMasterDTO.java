package com.restaurant.inventorysystem.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Author: Ram Choudhary
 * Date: 07-Nov-2025
 * Description: DTO for RecipeMaster. Keeps API clean without exposing entity.
 * Since: 07-Nov-2025
 */
public class RecipeMasterDTO {
    private Integer recipeId;
    private String recipeCode;
    private String recipeName;
    private String recipeType;
    private String description;
    private BigDecimal totalCost;
    private BigDecimal sellingPrice;
    private Integer preparationTime;
    private Integer activeFlag;
    private Integer enableFlag;
    private Integer createdById;
    private LocalDateTime createdDate;
    private Integer modifiedById;
    private LocalDateTime modifiedDate;

    // Getters & Setters
    public Integer getRecipeId() { return recipeId; }
    public void setRecipeId(Integer recipeId) { this.recipeId = recipeId; }
    public String getRecipeCode() { return recipeCode; }
    public void setRecipeCode(String recipeCode) { this.recipeCode = recipeCode; }
    public String getRecipeName() { return recipeName; }
    public void setRecipeName(String recipeName) { this.recipeName = recipeName; }
    public String getRecipeType() { return recipeType; }
    public void setRecipeType(String recipeType) { this.recipeType = recipeType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getTotalCost() { return totalCost; }
    public void setTotalCost(BigDecimal totalCost) { this.totalCost = totalCost; }
    public BigDecimal getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(BigDecimal sellingPrice) { this.sellingPrice = sellingPrice; }
    public Integer getPreparationTime() { return preparationTime; }
    public void setPreparationTime(Integer preparationTime) { this.preparationTime = preparationTime; }
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
