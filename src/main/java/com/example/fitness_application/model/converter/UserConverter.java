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
        userDTO.setCnp(user.getCnp());
        userDTO.setHeight(user.getHeight());
        userDTO.setWeight(user.getWeight());
        userDTO.setAge(user.getAge());
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
        user.setCnp(userDTO.getCnp());
        user.setHeight(userDTO.getHeight());
        user.setWeight(userDTO.getWeight());
        user.setAge(userDTO.getAge());
        if (userDTO.getGoal() != null) {
            user.setGoal(GoalConverter.toEntity(userDTO.getGoal()));
        }

        return user;
    }
}
