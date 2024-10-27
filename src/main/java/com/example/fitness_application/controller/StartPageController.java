package com.example.fitness_application.controller;

import com.example.fitness_application.model.dto.GoalDTO;
import com.example.fitness_application.model.dto.UserDTO;
import com.example.fitness_application.model.dto.WorkoutDTO;
import com.example.fitness_application.service.GoalService;
import com.example.fitness_application.service.TrainingProgramService;
import com.example.fitness_application.service.UserService;
import com.example.fitness_application.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
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

        if (role.equals("ROLE_USER")) {
            // Check if user exists in the database
            Optional<UserDTO> userOptional = userService.getUserByUsername(username);
            // If the user doesn't exist, redirect to the create user page
            if (userOptional.isEmpty()) {
                return "redirect:/users/new";
            } else {
                // If the user exists, display their details
                model.addAttribute("user", userOptional.get());
//                model.addAttribute() adauga workoutir
            }
        } else if (role.equals("ROLE_TRAINER")) {
            // For trainers, display all users and their training programs
            model.addAttribute("users", userService.getAllUsers());

        }  else if (role.equals("ROLE_ADMIN")) {
        // Pentru ADMIN, afișăm toți utilizatorii, toate obiectivele și toate antrenamentele cu butoane de editare și ștergere
        List<UserDTO> allUsers = userService.getAllUsers();
        List<GoalDTO> allGoals = goalService.getAllGoals();
        List<WorkoutDTO> allWorkouts = workoutService.getAllWorkouts();

        model.addAttribute("users", allUsers);
        model.addAttribute("goals", allGoals);
        model.addAttribute("workouts", allWorkouts);
    }


        return "start-page";
    }
}
