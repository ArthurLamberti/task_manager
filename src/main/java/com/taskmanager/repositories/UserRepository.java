package com.taskmanager.repositories;

import com.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.function.Supplier;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String name);

    Optional<User> findByEmail(String name);
}
