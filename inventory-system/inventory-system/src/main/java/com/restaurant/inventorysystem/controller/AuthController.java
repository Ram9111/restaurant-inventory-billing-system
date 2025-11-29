package com.restaurant.inventorysystem.controller;

import com.restaurant.inventorysystem.dto.LoginRequest;
import com.restaurant.inventorysystem.dto.LoginResponse;
import com.restaurant.inventorysystem.service.AuthService;
import com.restaurant.inventorysystem.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    /**
     * Handles user login and returns user details along with authentication status.
     *
     * @param request Login credentials (username and password)
     * @return LoginResponse containing status, message, and user details if successful
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}

