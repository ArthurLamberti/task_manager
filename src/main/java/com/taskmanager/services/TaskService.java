package com.taskmanager.services;

import com.taskmanager.dto.CreateTaskDTO;
import com.taskmanager.model.Task;

import java.util.List;

public interface TaskService {

    void create(CreateTaskDTO createTaskDTO);
    void delete();
    List<Task> list();
    void update();

}
