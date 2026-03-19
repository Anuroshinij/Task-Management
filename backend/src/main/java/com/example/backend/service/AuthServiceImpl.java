package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService  {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User register(User user) {

        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new RuntimeException("Email already exists");
                });

        return userRepository.save(user);
        
    }

    @Override
    public User login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!user.getPassword().equals(password))
        {
            throw new RuntimeException("Invalid Password");
        }

        return user;
        
    }

}
