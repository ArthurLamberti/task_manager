package com.taskmanager.controllers;

import com.taskmanager.model.User;
import com.taskmanager.repositories.UserRepository;
import com.taskmanager.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    UserController(
            final UserService userService
    ){
        this.userService = userService;
    }

    @RequestMapping("/auth")
    public User authAndDetailUser(Authentication authentication) {
        return userService.authUser(authentication);
    }

}
