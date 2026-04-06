package com.example.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.backend.model.Task;
import com.example.backend.model.User;
import com.example.backend.repository.TaskRepository;
import com.example.backend.repository.UserRepository;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    // 🔹 CREATE TASK
    @Override
    public Task createTask(Task task) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.setUser(user);

        // Optional: enforce uppercase consistency
        task.setStatus(task.getStatus().toUpperCase());
        task.setPriority(task.getPriority().toUpperCase());

        return taskRepository.save(task);
    }

    // 🔹 GET TASKS BY LOGGED-IN USER (SECURE VERSION)
    @Override
    public List<Task> getTaskByUser(User user) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User loggedInUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return taskRepository.findByUser(loggedInUser);
    }

    // 🔹 UPDATE TASK
    @Override
    public Task updateTask(Long id, Task task) {

        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        // 🔐 Ownership check
        if (!existing.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized to update this task");
        }

        existing.setTitle(task.getTitle());
        existing.setDescription(task.getDescription());
        existing.setStatus(task.getStatus().toUpperCase());
        existing.setPriority(task.getPriority().toUpperCase());
        existing.setDueDate(task.getDueDate()); // ✅ FIXED

        return taskRepository.save(existing);
    }

    // 🔹 DELETE TASK
    @Override
    public void deleteTask(Long id) {

        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        // 🔐 Ownership check
        if (!existing.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized to delete this task");
        }

        taskRepository.delete(existing);
    }
}