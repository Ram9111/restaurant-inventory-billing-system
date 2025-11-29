package com.restaurant.inventorysystem.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
/**
 * =========================================================================================================
 *  Entity Name   : IngredientsMaster
 *  Table Name    : ingredients_master
 *  Description   : Use the store the values of users which is register
 *  Author        : Ram Choudhary
 *  Created Date  : 02-Nav-2025
 *  Version       : 1.0
 * =========================================================================================================
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_Id")
    private int userId;

    @Column(name = "User_Name")
    private String userName;

    @Column(name = "Password")
    private String password;

    @Column(name = "Name")
    private String name;

    @Column(name = "Designation")
    private String designation;

    @Column(name = "Department")
    private String department;

    @Column(name = "User_Type")
    private Integer userType;

    @Column(name = "User_Email")
    private String userEmail;

    @Column(name = "UserRole_Id")
    private Integer userRoleId;

    @Column(name = "Create_Date")
    private LocalDateTime createDate;

    @Column(name = "Modify_Date")
    private LocalDateTime modifyDate;

    @Column(name = "Create_User_Id")
    private Integer createUserId;

    @Column(name = "Modify_User_Id")
    private Integer modifyUserId;

    @Column(name = "Active_Flag")
    private Integer activeFlag = 1;

    @Column(name = "Enable_Flag")
    private Integer enableFlag;

    // ---------------------------
    // Constructors
    // ---------------------------

    public User() {
        // No-argument constructor (required by JPA)
    }

    public User(int userId, String userName, String password, String name, String designation,
                String department, Integer userType, String userEmail, Integer userRoleId,
                LocalDateTime createDate, LocalDateTime modifyDate, Integer createUserId,
                Integer modifyUserId, Integer activeFlag, Integer enableFlag) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.designation = designation;
        this.department = department;
        this.userType = userType;
        this.userEmail = userEmail;
        this.userRoleId = userRoleId;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.createUserId = createUserId;
        this.modifyUserId = modifyUserId;
        this.activeFlag = activeFlag;
        this.enableFlag = enableFlag;
    }

    // ---------------------------
    // Getters and Setters
    // ---------------------------

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(Integer modifyUserId) {
        this.modifyUserId = modifyUserId;
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
