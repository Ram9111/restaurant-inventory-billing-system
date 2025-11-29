package com.restaurant.inventorysystem.service.impl;

import com.restaurant.inventorysystem.entity.User;
import com.restaurant.inventorysystem.repository.UserRepository;
import com.restaurant.inventorysystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        // Encrypt the password before saving
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Set audit fields
        user.setCreateDate(LocalDateTime.now());
        user.setActiveFlag(1);
        user.setEnableFlag(1);

        // Optional: You can use Java 8 Optional or Stream for validations or defaults
        Optional.ofNullable(user.getDepartment()).ifPresentOrElse(
                dept -> user.setDepartment(dept.trim()),
                () -> user.setDepartment("General")
        );

        return userRepository.save(user);
    }
}
