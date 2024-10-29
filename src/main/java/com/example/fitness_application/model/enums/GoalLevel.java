package com.example.fitness_application.model.enums;

import lombok.Getter;

@Getter
public enum GoalLevel {
    BEGINNER(0.8),
    INTERMEDIATE(1.0),
    ADVANCED(1.2);

    private final double adjustmentFactor;

    GoalLevel(double adjustmentFactor) {
        this.adjustmentFactor = adjustmentFactor;
    }

}
