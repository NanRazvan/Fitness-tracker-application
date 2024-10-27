package com.example.fitness_application.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GoalDTO {
    private Long id;
    private String goalType;
    private String level;
    private List<WorkoutDTO> workouts;
}
