package com.taskmanager.controllers;

import com.taskmanager.model.User;
import com.taskmanager.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserRepository userRepository;

    UserController(
            final UserRepository userRepository
    ){
        this.userRepository = userRepository;
    }

    @RequestMapping("/auth")
    public User authAndDetailUser(Authentication authentication) {
        return userRepository
                .findByUsername(authentication.getName())
                .or(() -> userRepository.findByEmail(authentication.getName()))
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

}
