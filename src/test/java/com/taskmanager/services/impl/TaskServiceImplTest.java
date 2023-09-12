package com.taskmanager.services.impl;

import com.taskmanager.constraints.ExceptionStrings;
import com.taskmanager.dto.CreateTaskDTO;
import com.taskmanager.exceptions.ValidationException;
import com.taskmanager.model.Task;
import com.taskmanager.model.User;
import com.taskmanager.repositories.TaskRepository;
import com.taskmanager.services.TaskService;
import com.taskmanager.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Captor
    ArgumentCaptor<Task> taskCaptor;

    @Test
    public void shouldValidateWithEndDateBeforeStartDate() {
        when(userService.getAuthenticatedUser()).thenReturn(getUser());
        CreateTaskDTO request = getRequestCreate()
                .endDate(LocalDateTime.now().minus(1, ChronoUnit.DAYS))
                .startDate(LocalDateTime.now())
                .build();

        ValidationException ve = Assertions.assertThrows(ValidationException.class, () -> taskService.create(request));

        assertEquals(ExceptionStrings.ENDDATE_BEFORE_STARTDATE, ve.getMessage());
    }

    @Test
    public void shouldValidateWithEstimatedDateBeforeStartDate() {
        when(userService.getAuthenticatedUser()).thenReturn(getUser());
        CreateTaskDTO request = getRequestCreate()
                .estimatedDate(LocalDateTime.now().minus(1, ChronoUnit.DAYS))
                .startDate(LocalDateTime.now())
                .build();

        ValidationException ve = Assertions.assertThrows(ValidationException.class, () -> taskService.create(request));

        assertEquals(ExceptionStrings.ESTIMATEDDATE_BEFORE_STARTDATE, ve.getMessage());
    }

    @Test
    public void shouldValidateWithEndDateAndStartDateNull() {
        when(userService.getAuthenticatedUser()).thenReturn(getUser());
        CreateTaskDTO request = getRequestCreate()
                .endDate(LocalDateTime.now().minus(1, ChronoUnit.DAYS))
                .build();

        ValidationException ve = Assertions.assertThrows(ValidationException.class, () -> taskService.create(request));

        assertEquals(ExceptionStrings.STARTDATE_NULL, ve.getMessage());
    }

    @Test
    public void shouldValidateWithEstimatedDateAndStartDateNull() {
        when(userService.getAuthenticatedUser()).thenReturn(getUser());
        CreateTaskDTO request = getRequestCreate()
                .estimatedDate(LocalDateTime.now().minus(1, ChronoUnit.DAYS))
                .build();

        ValidationException ve = Assertions.assertThrows(ValidationException.class, () -> taskService.create(request));

        assertEquals(ExceptionStrings.STARTDATE_NULL, ve.getMessage());
    }

    @Test
    public void shouldSaveRequest() {
        when(userService.getAuthenticatedUser()).thenReturn(getUser());
        CreateTaskDTO request = getRequestCreate()
                .estimatedDate(LocalDateTime.now().plus(2, ChronoUnit.DAYS))
                .startDate(LocalDateTime.now())
                .name("test save")
                .build();

        taskService.create(request);
        verify(taskRepository).save(taskCaptor.capture());
        Task task = taskCaptor.getValue();
        assertEquals(request.getName(), task.getName());
        assertEquals(request.getStartDate(), task.getStartDate());
        assertEquals(request.getEstimatedDate(), task.getEstimatedDate());
        assertNull(task.getEndDate());
    }

    private User getUser() {
        return User.builder().build();
    }

    private CreateTaskDTO.CreateTaskDTOBuilder getRequestCreate() {
        return CreateTaskDTO.builder()
                .name("abc");
    }

}