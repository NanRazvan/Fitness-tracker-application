package com.example.fitness_application.controller;

import com.example.fitness_application.model.entity.Goal;
import com.example.fitness_application.model.entity.Workout;
import com.example.fitness_application.repository.GoalRepository;
import com.example.fitness_application.repository.WorkoutRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/trainings")
public class TrainingController {

    private GoalRepository goalRepository;

    private WorkoutRepository workoutRepository;

    @GetMapping("/associate")
    public String showAssociationPage(Model model) {
        model.addAttribute("workouts", workoutRepository.findAll());
        model.addAttribute("goals", goalRepository.findAll());
        return "training-pages/associate-goals-workouts";
    }

    @GetMapping("/getWorkoutsForGoal/{goalId}")
    @ResponseBody
    public List<Map<String, Object>> getWorkoutsForGoal(@PathVariable Long goalId) {
        Goal goal = goalRepository.findById(goalId).orElseThrow(() -> new EntityNotFoundException("Goal not found"));
        List<Map<String, Object>> workouts = new ArrayList<>();

        for (Workout workout : workoutRepository.findAll()) {
            Map<String, Object> workoutMap = new HashMap<>();
            workoutMap.put("id", workout.getId());
            workoutMap.put("name", workout.getName());
            workoutMap.put("associated", goal.getWorkouts().contains(workout));
            workouts.add(workoutMap);
        }

        return workouts;
    }

    @GetMapping("/getGoalsForWorkout/{workoutId}")
    @ResponseBody
    public List<Map<String, Object>> getGoalsForWorkout(@PathVariable Long workoutId) {
        Workout workout = workoutRepository.findById(workoutId).orElseThrow(() -> new EntityNotFoundException("Workout not found"));
        List<Map<String, Object>> goals = new ArrayList<>();

        for (Goal goal : goalRepository.findAll()) {
            Map<String, Object> goalMap = new HashMap<>();
            goalMap.put("id", goal.getId());
            goalMap.put("goalType", goal.getGoalType());
            goalMap.put("associated", workout.getGoals().contains(goal));
            goals.add(goalMap);
        }

        return goals;
    }

    @PostMapping("/associate-goal")
    public String associateWorkoutsWithGoal(@RequestParam Long goalId, @RequestParam List<Long> workoutIds) {
        Goal goal = goalRepository.findById(goalId).orElseThrow(() -> new EntityNotFoundException("Goal not found"));
        List<Workout> workouts = workoutRepository.findAllById(workoutIds);
        goal.setWorkouts(workouts); // Setează lista de Workout-uri asociate
        goalRepository.save(goal); // Salvează Goal-ul cu noile asocieri
        return "redirect:/trainings/associate";
    }

    @PostMapping("/associate")
    public String associateGoalsWithWorkouts(@RequestParam List<String> workoutGoalAssociations) {
        // Curăță asocierile existente
        for (Workout workout : workoutRepository.findAll()) {
            workout.getGoals().clear();
        }
        for (Goal goal : goalRepository.findAll()) {
            goal.getWorkouts().clear();
        }

        for (String association : workoutGoalAssociations) {
            String[] ids = association.split("-");
            Long workoutId = Long.parseLong(ids[0]);
            Long goalId = Long.parseLong(ids[1]);

            Workout workout = workoutRepository.findById(workoutId).orElse(null);
            Goal goal = goalRepository.findById(goalId).orElse(null);

            if (workout != null && goal != null) {
                workout.getGoals().add(goal);
                goal.getWorkouts().add(workout);
                workoutRepository.save(workout);
                goalRepository.save(goal);
            }
        }

        return "redirect:/trainings/associate";
    }
}

