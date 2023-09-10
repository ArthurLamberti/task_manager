package com.taskmanager.services.impl;

import com.taskmanager.dto.CreateTaskDTO;
import com.taskmanager.model.User;
import com.taskmanager.repositories.TaskRepository;
import com.taskmanager.services.TaskService;
import com.taskmanager.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    public void shouldValidateWithPastStartDate() {
        Mockito.when(userService.getAuthenticatedUser()).thenReturn(getUser());
        CreateTaskDTO request = getRequestCreate()
                .startDate(LocalDateTime.now().minus(1, ChronoUnit.DAYS))
                .build();

        taskService.create(request);
    }

    private User getUser() {
        return User.builder().build();
    }

    private CreateTaskDTO.CreateTaskDTOBuilder getRequestCreate() {
        return CreateTaskDTO.builder()
                .name("abc");
    }

}