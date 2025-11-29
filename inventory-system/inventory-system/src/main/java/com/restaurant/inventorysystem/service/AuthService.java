package com.restaurant.inventorysystem.service;

import com.restaurant.inventorysystem.dto.LoginRequest;
import com.restaurant.inventorysystem.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
