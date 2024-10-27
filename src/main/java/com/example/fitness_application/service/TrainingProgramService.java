package com.example.fitness_application.service;

import com.example.fitness_application.model.converter.GoalConverter;
import com.example.fitness_application.model.converter.UserConverter;
import com.example.fitness_application.model.converter.WorkoutConverter;
import com.example.fitness_application.model.dto.WorkoutDTO;
import com.example.fitness_application.model.entity.Goal;
import com.example.fitness_application.model.entity.User;
import com.example.fitness_application.model.entity.Workout;
import com.example.fitness_application.repository.GoalRepository;
import com.example.fitness_application.repository.UserRepository;
import com.example.fitness_application.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainingProgramService {

    private final UserRepository userRepository;
    private final GoalRepository goalRepository;
    private final WorkoutRepository workoutRepository;

    public boolean assignGoalAndWorkoutsToUser(Long userId, Long goalId, List<Long> workoutIds) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Goal> optionalGoal = goalRepository.findById(goalId);
        List<Workout> workouts = workoutRepository.findAllById(workoutIds);

        if (optionalUser.isPresent() && optionalGoal.isPresent() && !workouts.isEmpty()) {
            User user = optionalUser.get();
            Goal goal = optionalGoal.get();

            user.setGoal(goal);
            goal.setWorkouts(workouts);

            userRepository.save(user);
            return true;
        }
        return false;
    }

    public List<WorkoutDTO> getWorkoutsByGoalId(Long goalId) {
        return goalRepository.findById(goalId)
                .map(goal -> goal.getWorkouts().stream()
                        .map(WorkoutConverter::toDTO)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }
}
