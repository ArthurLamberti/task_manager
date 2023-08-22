package com.taskmanager.services;

import com.taskmanager.model.User;
import org.springframework.security.core.Authentication;

public interface UserService {
    User authUser(Authentication authentication);
    User getUserById(Long id);
}
