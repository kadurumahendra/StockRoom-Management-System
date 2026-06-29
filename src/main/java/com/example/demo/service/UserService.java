package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String login(User user) {

        User dbUser = userRepository.findByUsername(user.getUsername());

        if (dbUser == null) {
            return "User not found";
        }

        if (dbUser.getPassword().equals(user.getPassword())) {
            return "Login Successful";
        }

        return "Invalid Password";
    }
}