package com.example.fitness_application.service;

import com.example.fitness_application.model.converter.GoalConverter;
import com.example.fitness_application.model.dto.GoalDTO;
import com.example.fitness_application.model.entity.Goal;
import com.example.fitness_application.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;

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
        if (goalDTO.getId() != null) {
            goalRepository.findById(goalDTO.getId()).ifPresent(existingGoal -> goal.setId(existingGoal.getId()));
        }
        goalRepository.save(goal);
    }

    public void deleteGoal(Long id) {
        goalRepository.deleteById(id);
    }
}
