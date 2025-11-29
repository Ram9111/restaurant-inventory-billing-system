package com.restaurant.inventorysystem.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity class representing a Recipe in the restaurant inventory system.
 * Stores details like recipe type, pricing, preparation time, and audit info.
 */
@Entity
@Table(name = "recipe_master")
public class RecipeMaster {

    /** Unique identifier for the recipe */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Integer recipeId;

    /** Unique code for the recipe (e.g., RCP001) */
    @Column(name = "recipe_code", length = 50, unique = true, nullable = false)
    private String recipeCode;

    /** Name of the recipe */
    @Column(name = "recipe_name", length = 100, nullable = false)
    private String recipeName;

    /** Type of recipe (e.g., Veg, Non-Veg, Dessert, Beverage) */
    @Column(name = "recipe_type", length = 30)
    private String recipeType;

    /** Short description or preparation details */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /** Total cost of preparing this recipe (auto-calculated from recipe_xref table) */
    @Column(name = "total_cost", precision = 10, scale = 2)
    private BigDecimal totalCost;

    /** Selling price of the recipe */
    @Column(name = "selling_price", precision = 10, scale = 2)
    private BigDecimal sellingPrice;

    /** Time required to prepare the recipe (in minutes) */
    @Column(name = "preparation_time")
    private Integer preparationTime;

    /** Active flag: 1 = Active, 0 = Inactive */
    @Column(name = "active_flag", nullable = false)
    private Integer activeFlag = 1;

    /** Enable flag: 1 = Enabled, 0 = Disabled */
    @Column(name = "enable_flag", nullable = false)
    private Integer enableFlag = 1;

    /** User who created the recipe */
    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private User createdBy;

    /** Timestamp when the recipe was created */
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    /** User who last modified the recipe */
    @ManyToOne
    @JoinColumn(name = "modified_by", referencedColumnName = "user_id")
    private User modifiedBy;

    /** Timestamp when the recipe was last modified */
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    // ------------------ Constructors ------------------

    /** Default constructor */
    public RecipeMaster() {}

    /** Parameterized constructor for easy object creation */
    public RecipeMaster(String recipeCode, String recipeName, String recipeType, String description,
                        BigDecimal totalCost, BigDecimal sellingPrice, Integer preparationTime,
                        Integer activeFlag, Integer enableFlag, User createdBy, User modifiedBy) {
        this.recipeCode = recipeCode;
        this.recipeName = recipeName;
        this.recipeType = recipeType;
        this.description = description;
        this.totalCost = totalCost;
        this.sellingPrice = sellingPrice;
        this.preparationTime = preparationTime;
        this.activeFlag = activeFlag;
        this.enableFlag = enableFlag;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
    }

    // ------------------ Getters and Setters ------------------

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeCode() {
        return recipeCode;
    }

    public void setRecipeCode(String recipeCode) {
        this.recipeCode = recipeCode;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeType() {
        return recipeType;
    }

    public void setRecipeType(String recipeType) {
        this.recipeType = recipeType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Integer preparationTime) {
        this.preparationTime = preparationTime;
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
