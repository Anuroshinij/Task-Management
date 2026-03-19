package com.example.backend.service;

import java.util.List;

import com.example.backend.model.Task;
import com.example.backend.model.User;

public interface TaskService {

    Task createTask(Task task);

    List<Task> getTaskByUser(User user);

    Task updateTask(Long id, Task task);

    void deleteTask(Long id);
    
}
