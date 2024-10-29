package com.example.fitness_application.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "GOAL")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String goalType;
    private String level;

    @ManyToMany
    @JoinTable(
            name = "goal_workouts",
            joinColumns = @JoinColumn(name = "goal_id"),
            inverseJoinColumns = @JoinColumn(name = "workout_id")
    )
    private List<Workout> workouts;

    @OneToMany(mappedBy = "goal")
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
        user.setGoal(this);
    }

    public void removeUser(User user) {
        users.remove(user);
        user.setGoal(null);
    }
}
