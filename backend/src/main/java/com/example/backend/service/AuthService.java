package com.example.backend.service;

import com.example.backend.model.User;

public interface AuthService {

    User register(User user);

    User login(String email, String password);

}
