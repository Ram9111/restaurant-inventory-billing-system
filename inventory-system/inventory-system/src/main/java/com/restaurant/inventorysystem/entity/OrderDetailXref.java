package com.restaurant.inventorysystem.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity: OrderDetailXref
 * -------------------------------------------------
 * Stores each recipe/item for an order.
 * Connected with OrderDetail and RecipeMaster.
 *
 * Author: Ram Choudhary
 * Date: 12-Nov-2025
 */
@Entity
@Table(name = "order_detail_xref")
public class OrderDetailXref {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_xref_id")
    private Integer orderDetailXrefId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderDetail orderDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", referencedColumnName = "recipe_id", nullable = false)
    private RecipeMaster recipeMaster;


    @Column(name = "recipe_name")
    private String recipeName;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "selling_price")
    private BigDecimal sellingPrice;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "modified_by", referencedColumnName = "user_id")
    private User modifiedBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "active_flag")
    private Integer activeFlag;

    @Column(name = "enable_flag")
    private Integer enableFlag;

    // Getters and Setters
    public Integer getOrderDetailXrefId() { return orderDetailXrefId; }
    public void setOrderDetailXrefId(Integer orderDetailXrefId) { this.orderDetailXrefId = orderDetailXrefId; }

    public OrderDetail getOrderDetail() { return orderDetail; }
    public void setOrderDetail(OrderDetail orderDetail) { this.orderDetail = orderDetail; }

    public RecipeMaster getRecipeMaster() { return recipeMaster; }
    public void setRecipeMaster(RecipeMaster recipeMaster) { this.recipeMaster = recipeMaster; }

    public String getRecipeName() { return recipeName; }
    public void setRecipeName(String recipeName) { this.recipeName = recipeName; }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    public BigDecimal getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(BigDecimal sellingPrice) { this.sellingPrice = sellingPrice; }

    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }

    public User getModifiedBy() { return modifiedBy; }
    public void setModifiedBy(User modifiedBy) { this.modifiedBy = modifiedBy; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public LocalDateTime getModifiedDate() { return modifiedDate; }
    public void setModifiedDate(LocalDateTime modifiedDate) { this.modifiedDate = modifiedDate; }

    public Integer getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Integer activeFlag) { this.activeFlag = activeFlag; }

    public Integer getEnableFlag() { return enableFlag; }
    public void setEnableFlag(Integer enableFlag) { this.enableFlag = enableFlag; }
}
