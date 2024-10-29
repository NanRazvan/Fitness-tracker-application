package com.example.fitness_application.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WorkoutDTO {

    private Long id;
    private String name;
    private String description;
    private int duration;
}
