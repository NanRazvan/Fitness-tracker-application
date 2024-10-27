package com.example.fitness_application.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String cnp; // New field
    private double height; // New field
    private double weight;
    private GoalDTO goal;

}
