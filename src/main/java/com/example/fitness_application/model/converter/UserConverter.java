package com.example.fitness_application.model.converter;

import com.example.fitness_application.model.dto.GoalDTO;
import com.example.fitness_application.model.dto.UserDTO;
import com.example.fitness_application.model.entity.User;

public class UserConverter {

    public static UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setCnp(user.getCnp()); // Convert new field
        userDTO.setHeight(user.getHeight()); // Convert new field
        userDTO.setWeight(user.getWeight()); // Convert new field
        // Convertim și goal-ul utilizatorului dacă acesta există
        if (user.getGoal() != null) {
            GoalDTO goalDTO = GoalConverter.toDTO(user.getGoal());
            userDTO.setGoal(goalDTO);
        }

        return userDTO;
    }

    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setCnp(userDTO.getCnp()); // Set new field
        user.setHeight(userDTO.getHeight()); // Set new field
        user.setWeight(userDTO.getWeight()); // Set new field
        // Asociază goal-ul dacă este prezent
        if (userDTO.getGoal() != null) {
            user.setGoal(GoalConverter.toEntity(userDTO.getGoal()));
        }

        return user;
    }
}
