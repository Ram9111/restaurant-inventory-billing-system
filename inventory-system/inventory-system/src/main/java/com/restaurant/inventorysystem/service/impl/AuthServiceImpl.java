package com.restaurant.inventorysystem.service.impl;

import com.restaurant.inventorysystem.dto.LoginRequest;
import com.restaurant.inventorysystem.dto.LoginResponse;
import com.restaurant.inventorysystem.entity.User;
import com.restaurant.inventorysystem.repository.UserRepository;
import com.restaurant.inventorysystem.security.JwtUtil;
import com.restaurant.inventorysystem.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * =====================================================================
     *  Project Name  : Restaurant Inventory System
     *  Method Name   : login
     *  Description   : Authenticates the user credentials, validates password,
     *                  and generates a JWT token upon successful login.
     *
     *  Author        : Ram Choudhary
     *  Created Date  : 10-Nov-2025
     *  Version       : 1.0
     * =====================================================================
     */
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUserName(request.getUsername());

        if (user == null) {
            throw new RuntimeException("Invalid username");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getUserName());
        return new LoginResponse(token, user.getUserName(), "Login successful", LocalDateTime.now());
    }
}
