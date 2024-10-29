package com.example.fitness_application.controller;

import com.example.fitness_application.model.dto.CaloriesReportDTO;
import com.example.fitness_application.model.dto.UserDTO;
import com.example.fitness_application.model.entity.User;
import com.example.fitness_application.service.UserService;
import com.example.fitness_application.service.WorkoutIntensityService;
import com.example.fitness_application.model.converter.UserConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/calories")
public class WorkoutIntensityController {

    private final WorkoutIntensityService workoutIntensityService;
    private final UserService userService;

    @GetMapping("/user/{userId}")
    public String getUserCaloriesReport(@PathVariable Long userId, Model model) {
        UserDTO userDTO = userService.getUserById(userId);
        User user = UserConverter.toEntity(userDTO);

        CaloriesReportDTO report = workoutIntensityService.generateCaloriesReportForUser(user);

        model.addAttribute("report", report);
        return "reports/user-calories-report";
    }
}
