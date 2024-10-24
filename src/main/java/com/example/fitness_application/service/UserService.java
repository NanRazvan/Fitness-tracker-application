package com.example.fitness_application.service;

import com.example.fitness_application.model.converter.UserConverter;
import com.example.fitness_application.model.dto.UserDTO;
import com.example.fitness_application.model.entity.User;
import com.example.fitness_application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserConverter::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(UserConverter::toDTO).orElse(null);
    }

    public void saveOrUpdateUser(UserDTO userDTO) {
        User user = UserConverter.toEntity(userDTO);
        if (userDTO.getId() != null) {
            userRepository.findById(userDTO.getId()).ifPresent(existingUser -> user.setId(existingUser.getId()));
        }
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
