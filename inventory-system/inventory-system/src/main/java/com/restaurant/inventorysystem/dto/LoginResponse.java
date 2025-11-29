package com.restaurant.inventorysystem.dto;

import java.time.LocalDateTime;
/**
 * LoginResponse
 * ------------------------------------------------------------
 * @author  Ram Choudhary
 * @date    03-Nov-2025
 * @purpose DTO (Data Transfer Object) used to send authentication
 *          response details from the server to the client after
 *          a successful or failed login attempt.
 *
 *          Contains authentication token, username, message,
 *          and timestamp of the login event.
 * ------------------------------------------------------------
 */
public class LoginResponse {
    private String token;
    private String username;
    private String message;
    private LocalDateTime loginTime;

    public LoginResponse() {}

    public LoginResponse(String token, String username, String message, LocalDateTime loginTime) {
        this.token = token;
        this.username = username;
        this.message = message;
        this.loginTime = loginTime;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getLoginTime() { return loginTime; }
    public void setLoginTime(LocalDateTime loginTime) { this.loginTime = loginTime; }
}

