package com.taskmanager.services.impl;

import com.taskmanager.dto.CreateTaskDTO;
import com.taskmanager.dto.UpdateTaskDTO;
import com.taskmanager.enums.TaskStatusEnum;
import com.taskmanager.model.Task;
import com.taskmanager.model.User;
import com.taskmanager.repositories.TaskRepository;
import com.taskmanager.services.TaskService;
import com.taskmanager.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public void create(final CreateTaskDTO createTaskDTO) {
        User user = userService.getAuthenticatedUser();
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
    public void delete(final Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("task not found!"));
        task.setActive(false);
        taskRepository.save(task);
        log.info("delete");
    }

    @Override
    public List<Task> list(final boolean all) {
        User user = userService.getAuthenticatedUser();
        if (all) {
            return taskRepository.findByUserId(user.getId());
        }
        return taskRepository.findByUserIdAndActive(user.getId(), true);
    }

    @Override
    public void update(long id, UpdateTaskDTO updateTaskDTO) {
        User user = userService.getAuthenticatedUser();
        Task task = taskRepository.findByIdAndUserId(id, user.getId())
                        .orElseThrow(()->new RuntimeException("Task not found"));

        task.setName(updateTaskDTO.getName());
        task.setStatus(updateTaskDTO.getStatus());
        task.setActive(updateTaskDTO.isActive());
        task.setUpdatedAt(updateTaskDTO.getUpdatedAt());
        taskRepository.save(task);
    }
}
