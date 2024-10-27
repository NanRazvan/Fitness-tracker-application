package com.example.fitness_application.model.converter;

import com.example.fitness_application.model.dto.WorkoutDTO;
import com.example.fitness_application.model.entity.Workout;

public class WorkoutConverter {

    public static WorkoutDTO toDTO(Workout workout) {
        WorkoutDTO workoutDTO = new WorkoutDTO();
        workoutDTO.setId(workout.getId());
        workoutDTO.setName(workout.getName());
        workoutDTO.setDescription(workout.getDescription());
        workoutDTO.setDuration(workout.getDuration());
        workoutDTO.setCaloriesBurned(workout.getCaloriesBurned());
        return workoutDTO;
    }

    public static Workout toEntity(WorkoutDTO workoutDTO) {
        Workout workout = new Workout();
        workout.setId(workoutDTO.getId());
        workout.setName(workoutDTO.getName());
        workout.setDescription(workoutDTO.getDescription());
        workout.setDuration(workoutDTO.getDuration());
        workout.setCaloriesBurned(workoutDTO.getCaloriesBurned());
        return workout;
    }
}
