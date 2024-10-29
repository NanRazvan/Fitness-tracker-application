package com.example.fitness_application.model.converter;

import com.example.fitness_application.model.dto.GoalDTO;
import com.example.fitness_application.model.entity.Goal;

import java.util.stream.Collectors;

public class GoalConverter {

    public static GoalDTO toDTO(Goal goal) {
        GoalDTO goalDTO = new GoalDTO();
        goalDTO.setId(goal.getId());
        goalDTO.setGoalType(goal.getGoalType());
        goalDTO.setLevel(goal.getLevel());

        goalDTO.setWorkouts(
                goal.getWorkouts().stream()
                        .map(WorkoutConverter::toDTO)
                        .collect(Collectors.toList())
        );

        return goalDTO;
    }

    public static Goal toEntity(GoalDTO goalDTO) {
        Goal goal = new Goal();
        goal.setId(goalDTO.getId());
        goal.setGoalType(goalDTO.getGoalType());
        goal.setLevel(goalDTO.getLevel());

        if (goalDTO.getWorkouts() != null) {
            goal.setWorkouts(
                    goalDTO.getWorkouts().stream()
                            .map(WorkoutConverter::toEntity)
                            .collect(Collectors.toList())
            );
        }

        return goal;
    }
}
