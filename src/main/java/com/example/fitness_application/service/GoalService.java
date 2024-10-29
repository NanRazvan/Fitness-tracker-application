package com.example.fitness_application.service;

import com.example.fitness_application.model.converter.GoalConverter;
import com.example.fitness_application.model.dto.GoalDTO;
import com.example.fitness_application.model.entity.Goal;
import com.example.fitness_application.model.entity.User;
import com.example.fitness_application.repository.GoalRepository;
import com.example.fitness_application.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    public List<GoalDTO> getAllGoals() {
        return goalRepository.findAll().stream()
                .map(GoalConverter::toDTO)
                .collect(Collectors.toList());
    }

    public GoalDTO getGoalById(Long id) {
        Optional<Goal> goal = goalRepository.findById(id);
        return goal.map(GoalConverter::toDTO).orElse(null);
    }

    public void saveOrUpdateGoal(GoalDTO goalDTO) {
        Goal goal = GoalConverter.toEntity(goalDTO);
        goalRepository.save(goal);
    }

    public void deleteGoal(Long goalId) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new EntityNotFoundException("Goal not found with ID: " + goalId));

        for (User user : goal.getUsers()) {
            user.setGoal(null);
            userRepository.save(user);
        }

        goalRepository.delete(goal);
    }
}
