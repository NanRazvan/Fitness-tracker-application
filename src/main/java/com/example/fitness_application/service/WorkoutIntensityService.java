package com.example.fitness_application.service;

import com.example.fitness_application.model.converter.GoalConverter;
import com.example.fitness_application.model.converter.WorkoutConverter;

import com.example.fitness_application.model.dto.CaloriesReportDTO;
import com.example.fitness_application.model.dto.WorkoutDTO;
import com.example.fitness_application.model.entity.User;
import com.example.fitness_application.model.entity.Workout;
import com.example.fitness_application.model.enums.WorkoutType;
import com.example.fitness_application.model.enums.GoalLevel;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WorkoutIntensityService {

    public double calculateIntensityFactorForWorkout(Workout workout, User user) {
        WorkoutType workoutType = WorkoutType.fromDisplayName(workout.getName());
        double intensityFactor = workoutType.getIntensityFactor();
        double goalLevelFactor = getGoalLevelFactor(user.getGoal().getLevel());

        return workout.getDuration() * intensityFactor * goalLevelFactor;
    }

    public CaloriesReportDTO generateCaloriesReportForUser(User user) {
        Map<WorkoutDTO, Double> intensityFactorsPerWorkout = user.getGoal().getWorkouts().stream()
                .collect(Collectors.toMap(
                        WorkoutConverter::toDTO,
                        workout -> calculateIntensityFactorForWorkout(workout, user)
                ));

        double totalCalories = intensityFactorsPerWorkout.values().stream()
                .mapToDouble(aDouble -> calculateCaloriesBurned(aDouble, user))
                .sum();

        return new CaloriesReportDTO(user.getName(), totalCalories, GoalConverter.toDTO(user.getGoal()), intensityFactorsPerWorkout);
    }

    private double calculateCaloriesBurned(double intensityFactor, User user) {
        double weightFactor = user.getWeight();
        double heightFactor = user.getHeight() / 100.0;
        double ageFactor = user.getAge();

        return intensityFactor * (weightFactor * 0.5 + heightFactor * 0.3 + ageFactor * 0.2);
    }

    private double getGoalLevelFactor(String goalLevel) {
        try {
            GoalLevel level = GoalLevel.valueOf(goalLevel.toUpperCase());
            return level.getAdjustmentFactor();
        } catch (IllegalArgumentException e) {
            return 1.0;
        }
    }
}
