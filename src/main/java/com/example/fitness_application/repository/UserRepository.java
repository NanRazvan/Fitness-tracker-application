package com.example.fitness_application.repository;

import com.example.fitness_application.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
