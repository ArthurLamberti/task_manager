package com.taskmanager.services;

import com.taskmanager.dto.CreateTaskDTO;
import com.taskmanager.dto.UpdateTaskDTO;
import com.taskmanager.model.Task;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface TaskService {

    void create(CreateTaskDTO createTaskDTO);
    void delete(Long taskId);
    List<Task> list(@RequestParam boolean all);
    void update(long id, UpdateTaskDTO updateTaskDTO);

}
