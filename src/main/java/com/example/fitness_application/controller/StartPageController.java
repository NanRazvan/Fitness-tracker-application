package com.example.fitness_application.controller;

import com.example.fitness_application.model.dto.UserDTO;
import com.example.fitness_application.service.GoalService;
import com.example.fitness_application.service.UserService;
import com.example.fitness_application.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class StartPageController {

    private final UserService userService;
    private final WorkoutService workoutService;
    private final GoalService goalService;

    @GetMapping("/start-page")
    public String showStartPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String role = authentication.getAuthorities().stream().findFirst().orElseThrow().getAuthority();

        switch (role) {
            case "ROLE_USER" -> {
                Optional<UserDTO> userOptional = userService.getUserByUsername(username);
                if (userOptional.isEmpty()) {
                    return "redirect:/users/new";
                } else {
                    model.addAttribute("user", userOptional.get());
                }
            }
            case "ROLE_TRAINER", "ROLE_ADMIN" -> {
                model.addAttribute("users", userService.getAllUsers());
                model.addAttribute("goals", goalService.getAllGoals());
                model.addAttribute("workouts", workoutService.getAllWorkouts());
            }
        }


        return "start-page";
    }
}
