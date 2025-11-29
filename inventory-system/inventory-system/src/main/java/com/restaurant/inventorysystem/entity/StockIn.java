package com.restaurant.inventorysystem.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity class representing a Stock In entry.
 * This single table stores both master and item details
 * such as stock number, date, supplier, quantity and stock updates.
 *
 * @author Ram
 * @date 2025-11-07
 * @purpose Handle stock-in operations (single table design)
 */
@Entity
@Table(name = "stock_in")
public class StockIn {

    // ------------------ Constructors ------------------

    /** Default constructor required by Hibernate & Jackson */
    public StockIn() {
    }

    /** Parameterized constructor (optional - for manual use) */
    public StockIn(String stockNo, LocalDateTime stockInDate, String supplierName,
                   IngredientsMaster ingredient, BigDecimal qtyMain, BigDecimal qtySub,
                   BigDecimal previousStockSub, BigDecimal updatedStockSub) {

        this.stockNo = stockNo;
        this.stockInDate = stockInDate;
        this.supplierName = supplierName;
        this.ingredient = ingredient;
        this.qtyMain = qtyMain;
        this.qtySub = qtySub;
        this.previousStockSub = previousStockSub;
        this.updatedStockSub = updatedStockSub;
    }


    // ------------------ Fields ------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_in_id")
    private Integer stockInId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private IngredientsMaster ingredient;

    @Column(name = "stock_no", nullable = false)
    private String stockNo;

    @Column(name = "stock_in_date", nullable = false)
    private LocalDateTime stockInDate;

    @Column(name = "supplier_name")
    private String supplierName;


    @Column(name = "qty_main", nullable = false)
    private BigDecimal qtyMain;

    @Column(name = "qty_sub", nullable = false)
    private BigDecimal qtySub;

    @Column(name = "previous_stock_sub", nullable = false)
    private BigDecimal previousStockSub;

    @Column(name = "updated_stock_sub", nullable = false)
    private BigDecimal updatedStockSub;

    @Column(name = "cost_per_unit")
    private BigDecimal costPerUnit;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @Column(name = "remarks")
    private String remarks;

    /** Created by (FK from User table) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private User createdBy;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by", referencedColumnName = "user_id")
    private User modifiedBy;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "active_flag", nullable = false)
    private Integer activeFlag = 1;

    @Column(name = "enable_flag", nullable = false)
    private Integer enableFlag = 1;



    // ------------------ Getters & Setters ------------------

    public Integer getStockInId() {
        return stockInId;
    }

    public void setStockInId(Integer stockInId) {
        this.stockInId = stockInId;
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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }



    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
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
    public IngredientsMaster getIngredient() {
        return ingredient;
    }

    public void setIngredient(IngredientsMaster ingredient) {
        this.ingredient = ingredient;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
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
