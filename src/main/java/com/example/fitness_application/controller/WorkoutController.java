package com.example.fitness_application.controller;

import com.example.fitness_application.model.dto.WorkoutDTO;
import com.example.fitness_application.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    @GetMapping
    public String getAllWorkouts(Model model) {
        model.addAttribute("workouts", workoutService.getAllWorkouts());
        return "workout-pages/list-workouts";
    }

    @GetMapping("/new")
    public String showNewWorkoutForm(Model model) {
        model.addAttribute("workout", new WorkoutDTO());
        return "workout-pages/workout-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditWorkoutForm(@PathVariable Long id, Model model) {
        WorkoutDTO workoutDTO = workoutService.getWorkoutById(id);
        if (workoutDTO != null) {
            model.addAttribute("workout", workoutDTO);
            return "workout-pages/workout-form";
        }
        return "redirect:/workouts";
    }

    @PostMapping("/save")
    public String saveOrUpdateWorkout(@ModelAttribute("workout") WorkoutDTO workoutDTO) {
        workoutService.saveOrUpdateWorkout(workoutDTO);
        return "redirect:/workouts";
    }

    @GetMapping("/delete/{id}")
    public String deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return "redirect:/workouts";
    }
}
