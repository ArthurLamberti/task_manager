package com.taskmanager.repositories;

import com.taskmanager.model.Role;
import com.taskmanager.model.Task;
import com.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long id);

    List<Task> findByUserIdAndActive(Long id, boolean all);

    Optional<Task> findByIdAndUserId(long id, Long userId);
}
