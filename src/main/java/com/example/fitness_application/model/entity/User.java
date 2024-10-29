package com.example.fitness_application.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String cnp;
    private int age;
    private double height;
    private double weight;

    @ManyToOne
    @JoinColumn(name = "goal_id")
    private Goal goal;

}
