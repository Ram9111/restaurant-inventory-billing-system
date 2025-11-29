package com.restaurant.inventorysystem.dto;
/**
 * LoginRequest
 * ------------------------------------------------------------
 * @author  Ram Choudhary
 * @date    10-Nov-2025
 * @purpose DTO (Data Transfer Object) used to carry login credentials
 *          such as username and password from client to server
 *          during the authentication process.
 * ------------------------------------------------------------
 */
public class LoginRequest {
    private String username;
    private String password;

    public LoginRequest() {}

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
