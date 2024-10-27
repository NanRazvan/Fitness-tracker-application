package com.example.fitness_application.model.converter;

import com.example.fitness_application.model.dto.GoalDTO;
import com.example.fitness_application.model.dto.WorkoutDTO;
import com.example.fitness_application.model.entity.Goal;

import java.util.stream.Collectors;

public class GoalConverter {

    public static GoalDTO toDTO(Goal goal) {
        GoalDTO goalDTO = new GoalDTO();
        goalDTO.setId(goal.getId());
        goalDTO.setGoalType(goal.getGoalType());
        goalDTO.setLevel(goal.getLevel());

        // Convertim lista de Workout în WorkoutDTO
        goalDTO.setWorkouts(
                goal.getWorkouts().stream()
                        .map(WorkoutConverter::toDTO) // Folosim WorkoutConverter pentru conversie
                        .collect(Collectors.toList())
        );

        return goalDTO;
    }

    public static Goal toEntity(GoalDTO goalDTO) {
        Goal goal = new Goal();
        goal.setId(goalDTO.getId());
        goal.setGoalType(goalDTO.getGoalType());
        goal.setLevel(goalDTO.getLevel());

        // Convertim lista de WorkoutDTO în Workout, dacă este nevoie
        if (goalDTO.getWorkouts() != null) {
            goal.setWorkouts(
                    goalDTO.getWorkouts().stream()
                            .map(WorkoutConverter::toEntity) // Conversia inversă în Workout
                            .collect(Collectors.toList())
            );
        }

        return goal;
    }
}
