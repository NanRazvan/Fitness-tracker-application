package com.example.fitness_application.controller;

import com.example.fitness_application.model.dto.GoalDTO;
import com.example.fitness_application.model.dto.UserDTO;
import com.example.fitness_application.service.UserService;
import com.example.fitness_application.service.GoalService;
import com.example.fitness_application.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final GoalService goalService;
    private final WorkoutService workoutService;

    @GetMapping
    public String getAllUsers(Model model) {
        // Obține numele utilizatorului logat
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("loggedInUsername", loggedInUsername);
        return "/start-page";
    }

    @GetMapping("/{id}/workouts")
    public String showUserWorkouts(@PathVariable Long id, Model model) {
        UserDTO userDTO = userService.getUserById(id);

        if (userDTO != null && userDTO.getGoal().getId() != null) {
            model.addAttribute("goal", userDTO.getGoal());
            model.addAttribute("workouts", userDTO.getGoal().getWorkouts());
            return "user-pages/user-workouts";
        }

        return "redirect:/users"; // Redirect dacă utilizatorul nu are goal sau nu există
    }

    @GetMapping("/new")
    public String showUserForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        // Create a new UserDTO with the prefilled username
        UserDTO userDTO = new UserDTO();
        userDTO.setName(name);
        List<GoalDTO> goals = goalService.getAllGoals();
        model.addAttribute("user", userDTO);
        model.addAttribute("goals", goals);
        return "user-pages/user-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        UserDTO userDTO = userService.getUserById(id);
        if (userDTO != null) {
            model.addAttribute("user", userDTO);
            model.addAttribute("goals", goalService.getAllGoals()); // Adăugăm lista de goals
            return "user-pages/user-form";
        } else {
            return "redirect:/start-page";
        }
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") UserDTO userDTO) {
        userService.saveOrUpdateUser(userDTO);
        return "redirect:/start-page";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/user-pages/list-users";
    }
}
