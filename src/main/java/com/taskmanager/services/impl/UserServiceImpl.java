package com.taskmanager.services.impl;

import com.taskmanager.model.User;
import com.taskmanager.repositories.UserRepository;
import com.taskmanager.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    UserServiceImpl(
            final UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    public User authUser(Authentication authentication) {
        return userRepository
                .findByUsername(authentication.getName())
                .or(() -> userRepository.findByEmail(authentication.getName()))
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository
                .findByUsername(authentication.getName())
                .or(() -> userRepository.findByEmail(authentication.getName()))
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

}
