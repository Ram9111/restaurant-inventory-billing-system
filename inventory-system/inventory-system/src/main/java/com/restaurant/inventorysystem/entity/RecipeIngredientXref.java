package com.restaurant.inventorysystem.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * Entity class representing the relationship (xref) between a Recipe and its Ingredients.
 *
 * <p>This table defines how much of each ingredient is used in a specific recipe,
 * along with measurement details, conversion rates, and audit information.</p>
 *
 * <p>Table: <b>recipe_master_ingredient_xref</b></p>
 *
 * @author Ram
 * @since 2025
 */
@Entity
@Table(name = "recipe_master_ingredient_xref")
public class RecipeIngredientXref {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_xref_id")
    private Integer recipeXrefId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private RecipeMaster recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private IngredientsMaster ingredient;

    @Column(name = "uom", length = 50)
    private String uom;

    @Column(name = "smaller_unit", length = 20)
    private String smallerUnit;

    @Column(name = "base_unit_value", precision = 10, scale = 2)
    private BigDecimal baseUnitValue;

    @Column(name = "quantity_value", precision = 10, scale = 2)
    private BigDecimal quantityValue;

    @Column(name = "remarks", length = 255)
    private String remarks;

    @Column(name = "active_flag")
    private Integer activeFlag;

    @Column(name = "enable_flag")
    private Integer enableFlag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by")
    private User modifiedBy;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    // ---------------- Constructors ----------------

    public RecipeIngredientXref() {
    }

    public RecipeIngredientXref(Integer recipeXrefId, RecipeMaster recipe, IngredientsMaster ingredient,
                                String uom, String smallerUnit, BigDecimal baseUnitValue,
                                BigDecimal quantityValue, String remarks,
                                Integer activeFlag, Integer enableFlag,
                                User createdBy, LocalDateTime createdDate,
                                User modifiedBy, LocalDateTime modifiedDate) {

        this.recipeXrefId = recipeXrefId;
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.uom = uom;
        this.smallerUnit = smallerUnit;
        this.baseUnitValue = baseUnitValue;
        this.quantityValue = quantityValue;
        this.remarks = remarks;
        this.activeFlag = activeFlag;
        this.enableFlag = enableFlag;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
    }

    // ---------------- Getters & Setters ----------------

    public Integer getRecipeXrefId() {
        return recipeXrefId;
    }

    public void setRecipeXrefId(Integer recipeXrefId) {
        this.recipeXrefId = recipeXrefId;
    }

    public RecipeMaster getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeMaster recipe) {
        this.recipe = recipe;
    }

    public IngredientsMaster getIngredient() {
        return ingredient;
    }

    public void setIngredient(IngredientsMaster ingredient) {
        this.ingredient = ingredient;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

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

    public BigDecimal getQuantityValue() {
        return quantityValue;
    }

    public void setQuantityValue(BigDecimal quantityValue) {
        this.quantityValue = quantityValue;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public User getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(User modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
