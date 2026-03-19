package com.example.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.model.Task;
import com.example.backend.model.User;
import com.example.backend.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task createTask(Task task) {

        return taskRepository.save(task);
       
    }

    @Override
    public List<Task> getTaskByUser(User user) {

        return taskRepository.findByUser(user);
        
    }

    @Override
    public Task updateTask(Long id, Task task) {

        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        existing.setTitle(task.getTitle());
        existing.setDescription(task.getDescription());
        existing.setStatus(task.getStatus());
        existing.setPriority(task.getPriority());

        return taskRepository.save(existing);
        
    }

    @Override
    public void deleteTask(Long id) {

        taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        taskRepository.deleteById(id);
        
    }



}
