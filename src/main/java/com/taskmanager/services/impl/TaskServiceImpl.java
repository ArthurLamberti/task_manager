package com.taskmanager.services.impl;

import com.taskmanager.constraints.ExceptionStrings;
import com.taskmanager.dto.CreateTaskDTO;
import com.taskmanager.dto.UpdateTaskDTO;
import com.taskmanager.enums.TaskStatusEnum;
import com.taskmanager.exceptions.ValidationException;
import com.taskmanager.model.Task;
import com.taskmanager.model.User;
import com.taskmanager.repositories.TaskRepository;
import com.taskmanager.services.TaskService;
import com.taskmanager.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.taskmanager.constraints.ExceptionStrings.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

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

        if (nonNull(createTaskDTO.getStartDate())) {
            if (nonNull(createTaskDTO.getEndDate()) && createTaskDTO.getEndDate().isBefore(createTaskDTO.getStartDate())) {
                throw new ValidationException(ENDDATE_BEFORE_STARTDATE);
            }

            if (nonNull(createTaskDTO.getEstimatedDate()) && createTaskDTO.getEstimatedDate().isBefore(createTaskDTO.getStartDate())) {
                throw new ValidationException(ESTIMATEDDATE_BEFORE_STARTDATE);
            }
        }

        if (
                (nonNull(createTaskDTO.getEndDate())
                        || nonNull(createTaskDTO.getEstimatedDate())
                ) && isNull(createTaskDTO.getStartDate())) {
            throw new ValidationException(STARTDATE_NULL);
        }

        Task task = Task.builder()
                .createdAt(LocalDateTime.now())
                .name(createTaskDTO.getName())
                .user(user)
                .status(TaskStatusEnum.TODO)
                .active(true)
                .startDate(createTaskDTO.getStartDate())
                .endDate(createTaskDTO.getEndDate())
                .estimatedDate(createTaskDTO.getEstimatedDate())
                .build();
        taskRepository.save(task);
    }

    @Override
    public void delete(final Long taskId) {
        User user = userService.getAuthenticatedUser();
        Task task = taskRepository.findByIdAndUserId(taskId, user.getId()).orElseThrow(() -> new RuntimeException("task not found!"));
        task.setActive(false);
        taskRepository.save(task);
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
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (nonNull(updateTaskDTO.getName()))
            task.setName(updateTaskDTO.getName());
        if (nonNull(updateTaskDTO.getStatus()))
            task.setStatus(updateTaskDTO.getStatus());
        if (nonNull(updateTaskDTO.getActive()))
            task.setActive(updateTaskDTO.getActive());

        task.setUpdatedAt(updateTaskDTO.getUpdatedAt());
        taskRepository.save(task);
    }
}
