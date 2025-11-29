package com.restaurant.inventorysystem.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * =========================================================================================================
 *  Entity Name   : IngredientsMaster
 *  Table Name    : ingredients_master
 *  Description   : Represents the ingredients used in the restaurant inventory system.
 *                  This entity stores details such as name, category, cost, unit of measure,
 *                  and audit information like createdBy, updatedBy, etc.
 *
 *  Relationships :
 *   - User (Many-to-One for createdBy & updatedBy)
 *
 *  Notes :
 *   - Uses Integer flags (1 = Active/Enabled, 0 = Inactive/Disabled)
 *   - FetchType.LAZY for better performance
 *
 *  Author        : Ram Choudhary
 *  Created Date  : 03-Nav-2025
 *  Version       : 1.0
 * =========================================================================================================
 */
@Entity
@Table(name = "ingredients_master")
public class IngredientsMaster {

    /** Primary Key: Auto-generated Ingredient ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Integer ingredientId;

    /** Unique ingredient code for identification */
    @Column(name = "ingredient_code", nullable = false, unique = true, length = 50)
    private String ingredientCode;

    /** Name of the ingredient */
    @Column(name = "ingredient_name", nullable = false, length = 100)
    private String ingredientName;

    /** Description or details about the ingredient */
    @Column(name = "ingredient_description", length = 255)
    private String ingredientDescription;

    /** Category (e.g., Dairy, Vegetables, Spices, etc.) */
    @Column(name = "category", length = 100)
    private String category;

    /** Unit of Measurement (e.g., KG, Litre, Packet) */
    @Column(name = "uom", length = 50)
    private String uom;


    @Column(name = "smaller_unit", nullable = false, length = 20)
    private String smallerUnit;

    @Column(name = "base_unit_value", nullable = false)
    private BigDecimal baseUnitValue = BigDecimal.ONE;

    @Column(name = "current_stock_subunit", nullable = false)
    private BigDecimal currentStockSubunit = BigDecimal.ZERO;


    /** Cost of the ingredient */
    @Column(name = "cost", precision = 10, scale = 2)
    private BigDecimal cost;

    /** Additional notes or comments */
    @Column(name = "notes", length = 500)
    private String notes;

    /** Created by (FK from User table) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private User createdBy;

    /** Record creation timestamp */
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    /** Updated by (FK from User table) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", referencedColumnName = "user_id")
    private User updatedBy;

    /** Record last updated timestamp */
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    /** 1 = Active, 0 = Inactive */
    @Column(name = "active_flag")
    private Integer activeFlag = 1;

    /** 1 = Enabled, 0 = Disabled */
    @Column(name = "enable_flag")
    private Integer enableFlag = 1;

    // ===========================
    // Constructors
    // ===========================

    /** Default constructor (required by JPA) */
    public IngredientsMaster() {
    }

    /**
     * Parameterized constructor to initialize all fields.
     * Used for creating objects manually or in test cases.
     */
    public IngredientsMaster(Integer ingredientId, String ingredientCode, String ingredientName,
                             String ingredientDescription, String category, String uom, String smallerUnit, BigDecimal baseUnitValue, BigDecimal currentStockSubunit, BigDecimal cost,
                             String notes, User createdBy,
                             LocalDateTime createdDate, User updatedBy, LocalDateTime updatedDate,
                             Integer activeFlag, Integer enableFlag) {
        this.ingredientId = ingredientId;
        this.ingredientCode = ingredientCode;
        this.ingredientName = ingredientName;
        this.ingredientDescription = ingredientDescription;
        this.category = category;
        this.uom = uom;
        this.smallerUnit = smallerUnit;
        this.baseUnitValue = baseUnitValue;
        this.currentStockSubunit = currentStockSubunit;
        this.cost = cost;
        this.notes = notes;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.activeFlag = activeFlag;
        this.enableFlag = enableFlag;
    }

    // ===========================
    // Getters and Setters
    // ===========================

    public Integer getIngredientId() { return ingredientId; }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientCode() {
        return ingredientCode;
    }

    public void setIngredientCode(String ingredientCode) {
        this.ingredientCode = ingredientCode;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getIngredientDescription() {
        return ingredientDescription;
    }

    public void setIngredientDescription(String ingredientDescription) { this.ingredientDescription = ingredientDescription; }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }



    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
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

    public BigDecimal getBaseUnitValue() {
        return baseUnitValue;
    }

    public void setBaseUnitValue(BigDecimal baseUnitValue) {
        this.baseUnitValue = baseUnitValue;
    }

    public String getSmallerUnit() {
        return smallerUnit;
    }

    public void setSmallerUnit(String smallerUnit) {
        this.smallerUnit = smallerUnit;
    }

    public BigDecimal getCurrentStockSubunit() { return currentStockSubunit; }

    public void setCurrentStockSubunit(BigDecimal currentStockSubunit) { this.currentStockSubunit = currentStockSubunit; }
}
