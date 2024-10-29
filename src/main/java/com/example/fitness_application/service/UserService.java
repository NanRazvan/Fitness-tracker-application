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

        User existingUser = userDTO.getId() != null ? userRepository.findById(userDTO.getId()).orElse(null) : null;
        Goal previousGoal = existingUser != null ? existingUser.getGoal() : null;

        Goal newGoal = userDTO.getGoal() != null ? goalRepository.findById(userDTO.getGoal().getId()).orElse(null) : null;
        user.setGoal(newGoal);
        if (previousGoal != null && !previousGoal.equals(newGoal)) {
            previousGoal.removeUser(user);
        }
        if (newGoal != null && (existingUser == null || !newGoal.getUsers().contains(user))) {
            newGoal.addUser(user);
        }
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
