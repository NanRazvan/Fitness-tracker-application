package com.example.fitness_application.service;

import com.example.fitness_application.model.converter.UserConverter;
import com.example.fitness_application.model.dto.UserDTO;
import com.example.fitness_application.model.entity.Goal;
import com.example.fitness_application.model.entity.User;
import com.example.fitness_application.repository.UserRepository;
import com.example.fitness_application.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GoalRepository goalRepository;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserConverter::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(UserConverter::toDTO).orElse(null);
    }

    public Optional<UserDTO> getUserByUsername(String name) {
        return userRepository.findByName(name)
                .map(UserConverter::toDTO);
    }


    public void saveOrUpdateUser(UserDTO userDTO) {
        User user = UserConverter.toEntity(userDTO);

        // Asociază goal-ul, dacă `goalId` este prezent
        if (userDTO.getGoal() != null) {
            Optional<Goal> goal = goalRepository.findById(userDTO.getGoal().getId());
            goal.ifPresent(user::setGoal);
        }

        if (userDTO.getId() != null) {
            userRepository.findById(userDTO.getId()).ifPresent(existingUser -> user.setId(existingUser.getId()));
        }
        user.setEmail(userDTO.getEmail());
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
