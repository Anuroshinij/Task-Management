package com.example.backend.service;

import com.example.backend.model.User;

public interface AuthService {

    User register(User user);

    String login(String email, String password);

}
