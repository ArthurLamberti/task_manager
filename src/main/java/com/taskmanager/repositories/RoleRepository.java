package com.taskmanager.repositories;

import com.taskmanager.model.Role;
import com.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> findByUser(User user);
}
