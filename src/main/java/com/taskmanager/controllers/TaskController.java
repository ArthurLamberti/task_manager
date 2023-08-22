package com.taskmanager.controllers;

import com.taskmanager.dto.CreateTaskDTO;
import com.taskmanager.model.Task;
import com.taskmanager.services.TaskService;
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
    public List<Task> listByUser() {
        return this.taskService.list();
    }

    @PostMapping("/tasks")
    public void create(@RequestBody CreateTaskDTO createTaskDTO) {
        this.taskService.create(createTaskDTO);
    }

    @PutMapping("/tasks")
    public void update() {
        this.taskService.update();
    }

    @DeleteMapping("/tasks")
    public void delete() {
        this.taskService.delete();
    }
}
