package com.example.fitness_application.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String cnp;
    private Integer age;
    private double height;
    private double weight;
    private GoalDTO goal;

}
