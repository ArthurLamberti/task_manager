package com.taskmanager.services.impl;

import com.taskmanager.dto.CreateTaskDTO;
import com.taskmanager.enums.TaskStatusEnum;
import com.taskmanager.model.Task;
import com.taskmanager.model.User;
import com.taskmanager.repositories.TaskRepository;
import com.taskmanager.services.TaskService;
import com.taskmanager.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    TaskServiceImpl(
            final TaskRepository taskRepository,
            final UserService userService
    ) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }


    @Override
    public void create(CreateTaskDTO createTaskDTO) {
        User user = userService.getUserById(createTaskDTO.getUserId());
        Task task = Task.builder()
                .createdAt(LocalDateTime.now())
                .name(createTaskDTO.getName())
                .user(user)
                .status(TaskStatusEnum.TODO)
                .active(true)
                .build();
        taskRepository.save(task);
    }

    @Override
    public void delete() {
        log.info("delete");
    }

    @Override
    public List<Task> list() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByAuthentication(auth);
        return taskRepository.findByUserId(user.getId());
    }

    @Override
    public void update() {
        log.info("update");
    }
}
