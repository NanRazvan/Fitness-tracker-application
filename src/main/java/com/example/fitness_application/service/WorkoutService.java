package com.example.fitness_application.service;

import com.example.fitness_application.model.converter.WorkoutConverter;
import com.example.fitness_application.model.dto.WorkoutDTO;
import com.example.fitness_application.model.entity.Workout;
import com.example.fitness_application.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    public List<WorkoutDTO> getAllWorkouts() {
        return workoutRepository.findAll().stream()
                .map(WorkoutConverter::toDTO)
                .collect(Collectors.toList());
    }

    public WorkoutDTO getWorkoutById(Long id) {
        Optional<Workout> workout = workoutRepository.findById(id);
        return workout.map(WorkoutConverter::toDTO).orElse(null);
    }

    public void saveOrUpdateWorkout(WorkoutDTO workoutDTO) {
        Workout workout = WorkoutConverter.toEntity(workoutDTO);
        workoutRepository.save(workout);
    }

    public void deleteWorkout(Long id) {
        workoutRepository.deleteById(id);
    }
}
