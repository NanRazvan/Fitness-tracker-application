package com.example.fitness_application.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int duration;
    private int caloriesBurned;

    @ManyToMany(mappedBy = "workouts")
    private List<Goal> goals; // Relatie many-to-many cu goal-urile

}
