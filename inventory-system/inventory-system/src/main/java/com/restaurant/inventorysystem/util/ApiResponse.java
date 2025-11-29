package com.restaurant.inventorysystem.util;
/**
 * =====================================================================
 *  Project Name  : Restaurant Inventory System
 *  Class Name    : ApiResponse
 *  Description   : A utility class used as a standard response model for
 *                  REST APIs. It encapsulates the response status code,
 *                  message, and optional data payload for consistent API
 *                  responses across the system.
 *
 *  Author        : Ram Choudhary
 *  Created Date  : 04-Nov-2025
 *  Version       : 1.0
 * =====================================================================
 */

public class ApiResponse {
    private int status;
    private String message;
    private Object data;

    public ApiResponse() {}

    public ApiResponse(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    public ApiResponse(int status, String message) {
        this.status = status;
        this.message = message;
           }
    // Getters & Setters
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
}
