package com.example.fitness_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.fitness_application.model.entity.Workout;

public interface WorkoutRepository extends JpaRepository<Workout,Long> {
}
