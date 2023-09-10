package com.taskmanager.controllers;

import com.taskmanager.dto.CreateTaskDTO;
import com.taskmanager.dto.UpdateTaskDTO;
import com.taskmanager.model.Task;
import com.taskmanager.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    private final TaskService taskService;

    TaskController(
            final TaskService taskService
    ) {
        this.taskService = taskService;
    }


    @GetMapping("/tasks")
    public List<Task> listByUser(@RequestParam(required = false) boolean all) {
        return this.taskService.list(all);
    }

    @PostMapping("/tasks")
    public void create(@RequestBody @Valid CreateTaskDTO createTaskDTO) {
        this.taskService.create(createTaskDTO);
    }

    @PutMapping("/tasks/{id}")
    public void update(@PathVariable("id") long id,@RequestBody UpdateTaskDTO updateTaskDTO) {
        this.taskService.update(id, updateTaskDTO);
    }

    @DeleteMapping("/tasks/{id}")
    public void delete(
            @PathVariable("id") Long taskId
    ) {
        this.taskService.delete(taskId);
    }
}
