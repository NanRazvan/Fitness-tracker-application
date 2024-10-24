package com.example.fitness_application.controller;

import com.example.fitness_application.entity.User;
import com.example.fitness_application.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/testUser")
    public ResponseEntity<String> testUser() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setAge(25);
        user.setHeight(1.80);
        user.setWeight(75.0);

        userRepository.save(user);
        return ResponseEntity.ok("User saved successfully");
    }
}
