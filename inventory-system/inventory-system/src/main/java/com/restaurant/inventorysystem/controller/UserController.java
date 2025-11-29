package com.restaurant.inventorysystem.controller;

import com.restaurant.inventorysystem.entity.User;
import com.restaurant.inventorysystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // optional, allows frontend access
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * Registers a new user into the system.
     *
     * @param user User object containing registration details
     * @return Saved User entity with generated ID and timestamps
     */

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }
}

