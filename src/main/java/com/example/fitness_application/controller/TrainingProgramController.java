package com.example.fitness_application.controller;

import com.example.fitness_application.model.dto.UserDTO;
import com.example.fitness_application.model.dto.GoalDTO;
import com.example.fitness_application.model.dto.WorkoutDTO;
import com.example.fitness_application.service.TrainingProgramService;
import com.example.fitness_application.service.UserService;
import com.example.fitness_application.service.GoalService;
import com.example.fitness_application.service.WorkoutService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TrainingProgramController {

    private final TrainingProgramService trainingProgramService;
    private final UserService userService;
    private final GoalService goalService;
    private final WorkoutService workoutService;

    public TrainingProgramController(TrainingProgramService trainingProgramService, UserService userService, GoalService goalService, WorkoutService workoutService) {
        this.trainingProgramService = trainingProgramService;
        this.userService = userService;
        this.goalService = goalService;
        this.workoutService = workoutService;
    }

    @GetMapping("/start-training")
    public String showTrainingProgramPage(Model model) {
        List<UserDTO> users = userService.getAllUsers();
        List<GoalDTO> goals = goalService.getAllGoals();
        model.addAttribute("users", users);
        model.addAttribute("goals", goals);
        return "start-training";
    }

    @PostMapping("/create-training-program")
    public String createTrainingProgram(
            @RequestParam("userId") Long userId,
            @RequestParam("goalId") Long goalId,
            @RequestParam("workoutIds") List<Long> workoutIds,
            Model model) {

        boolean isCreated = trainingProgramService.assignGoalAndWorkoutsToUser(userId, goalId, workoutIds);

        model.addAttribute("message", isCreated ? "Training program created successfully." : "Failed to create training program.");
        return "redirect:/start-page";
    }

    @GetMapping("/get-workouts-for-goal")
    public String getWorkoutsForGoal(@RequestParam("goalId") Long goalId, Model model) {
        List<WorkoutDTO> workouts = trainingProgramService.getWorkoutsByGoalId(goalId);
        model.addAttribute("workouts", workouts);
        return "workout-list";
    }
}
