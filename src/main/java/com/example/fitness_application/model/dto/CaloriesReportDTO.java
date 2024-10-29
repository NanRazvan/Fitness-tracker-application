package com.example.fitness_application.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class CaloriesReportDTO {
    private String userName;
    private double totalCalories;
    private GoalDTO goal;
    private Map<WorkoutDTO, Double> caloriesPerWorkout;

}