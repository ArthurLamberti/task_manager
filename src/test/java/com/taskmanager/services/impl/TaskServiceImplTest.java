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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

    private final Long ID_USER = 123L;


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
        assertEquals(TaskStatusEnum.TODO, task.getStatus());
        assertNull(task.getEndDate());
    }

    @Test
    public void shouldDeleteTask() {
        when(userService.getAuthenticatedUser()).thenReturn(getUser());
        when(taskRepository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(getTask()));

        taskService.delete(new Random().nextLong());
        verify(taskRepository).save(taskCaptor.capture());
        Task task = taskCaptor.getValue();
        assertFalse(task.isActive());
    }

    @Test
    public void shouldUpdateNameTask() {
        when(userService.getAuthenticatedUser()).thenReturn(getUser());
        when(taskRepository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(getTask()));
        UpdateTaskDTO request = getRequestUpdate()
                .name("upd").build();

        taskService.update(111L, request);

        verify(taskRepository).save(taskCaptor.capture());
        Task task = taskCaptor.getValue();

        assertEquals(request.getName(), task.getName());
        assertEquals(TaskStatusEnum.TODO, task.getStatus());
        assertTrue(task.isActive());
    }

    @Test
    public void shouldUpdateStatusTask() {
        when(userService.getAuthenticatedUser()).thenReturn(getUser());
        when(taskRepository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(getTask()));
        UpdateTaskDTO request = getRequestUpdate()
                .status(TaskStatusEnum.RUNNING).build();

        taskService.update(111L, request);

        verify(taskRepository).save(taskCaptor.capture());
        Task task = taskCaptor.getValue();

        assertEquals(getTask().getName(), task.getName());
        assertEquals(TaskStatusEnum.RUNNING, task.getStatus());
        assertTrue(task.isActive());
    }

    @Test
    public void shouldUpdateActiveTask() {
        when(userService.getAuthenticatedUser()).thenReturn(getUser());
        when(taskRepository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(getTask()));
        UpdateTaskDTO request = getRequestUpdate()
                .active(false).build();

        taskService.update(111L, request);

        verify(taskRepository).save(taskCaptor.capture());
        Task task = taskCaptor.getValue();

        assertEquals(getTask().getName(), task.getName());
        assertEquals(getTask().getStatus(), task.getStatus());
        assertFalse(task.isActive());
    }

    private User getUser() {
        return User.builder()
                .id(ID_USER)
                .email("test@mail.com")
                .username("testuser")
                .build();
    }

    private Task getTask() {
        return Task.builder()
                .id(111L)
                .user(getUser())
                .status(TaskStatusEnum.TODO)
                .name("test")
                .active(true)
                .build();
    }

    private CreateTaskDTO.CreateTaskDTOBuilder getRequestCreate() {
        return CreateTaskDTO.builder()
                .name("abc");
    }

    private UpdateTaskDTO.UpdateTaskDTOBuilder getRequestUpdate() {
        return UpdateTaskDTO.builder();
    }

}