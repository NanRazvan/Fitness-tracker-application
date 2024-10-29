package com.example.fitness_application.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum WorkoutType {
    WEIGHT_LIFTING("Weight Lifting", 0.05),
    CYCLING("Cycling", 0.045),
    SWIMMING("Swimming", 0.06),
    HIIT("HIIT", 0.07),
    PILATES("Pilates", 0.035),
    TREADMILL_WALKING("Treadmill Walking", 0.03),
    YOGA("Yoga", 0.025),
    RUNNING("Running", 0.06),
    ROWING("Rowing", 0.05),
    KICKBOXING("Kickboxing", 0.065),
    DANCING("Dancing", 0.04),
    JUMP_ROPE("Jump Rope", 0.075),
    CLIMBING("Climbing", 0.07),
    BOXING("Boxing", 0.065),
    HIKING("Hiking", 0.04),
    MARTIAL_ARTS("Martial Arts", 0.07),
    SPINNING("Spinning", 0.05),
    SKIING("Skiing", 0.055),
    SURFING("Surfing", 0.045);

    private final String displayName;
    @Getter
    private final double intensityFactor;
    private static final Map<String, WorkoutType> NAME_MAP = new HashMap<>();

    WorkoutType(String displayName, double intensityFactor) {
        this.displayName = displayName;
        this.intensityFactor = intensityFactor;
    }

    static {
        for (WorkoutType type : WorkoutType.values()) {
            NAME_MAP.put(type.displayName.toLowerCase(), type);
        }
    }

    public static WorkoutType fromDisplayName(String name) {
        return NAME_MAP.get(name.toLowerCase());
    }
}
