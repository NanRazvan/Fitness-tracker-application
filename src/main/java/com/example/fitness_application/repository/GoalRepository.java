package com.example.fitness_application.repository;

import com.example.fitness_application.model.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal,Long> {
}
