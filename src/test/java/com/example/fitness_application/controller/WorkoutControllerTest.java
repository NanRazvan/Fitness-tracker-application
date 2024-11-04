package com.example.fitness_application.controller;

import com.example.fitness_application.model.dto.WorkoutDTO;
import com.example.fitness_application.service.WorkoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WorkoutControllerTest {

    private static final Long TEST_ID = 1234L;

    @Mock
    private WorkoutService workoutService;

    @Mock
    private Model model;

    @InjectMocks
    private WorkoutController workoutController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllWorkouts() {
        String viewName = workoutController.getAllWorkouts(model);

        verify(model).addAttribute("workouts", workoutService.getAllWorkouts());
        assertEquals("redirect:/start-page", viewName);
    }

    @Test
    void testShowNewWorkoutForm() {
        String viewName = workoutController.showNewWorkoutForm(model);

        verify(model).addAttribute(eq("workout"), any(WorkoutDTO.class));
        assertEquals("workout-pages/workout-form", viewName);
    }

    @Test
    void testShowEditWorkoutForm_WithExistingWorkout() {
        WorkoutDTO workoutDTO = new WorkoutDTO();
        when(workoutService.getWorkoutById(TEST_ID)).thenReturn(workoutDTO);

        String viewName = workoutController.showEditWorkoutForm(TEST_ID, model);

        verify(model).addAttribute("workout", workoutDTO);
        assertEquals("workout-pages/workout-form", viewName);
    }

    @Test
    void testShowEditWorkoutForm_WithNonExistingWorkout() {
        when(workoutService.getWorkoutById(TEST_ID)).thenReturn(null);

        String viewName = workoutController.showEditWorkoutForm(TEST_ID, model);

        assertEquals("redirect:/workouts", viewName);
    }

    @Test
    void testSaveOrUpdateWorkout() {
        WorkoutDTO workoutDTO = new WorkoutDTO();

        String viewName = workoutController.saveOrUpdateWorkout(workoutDTO);

        verify(workoutService).saveOrUpdateWorkout(workoutDTO);
        assertEquals("redirect:/workouts", viewName);
    }

    @Test
    void testDeleteWorkout() {
        String viewName = workoutController.deleteWorkout(TEST_ID);

        verify(workoutService).deleteWorkout(TEST_ID);
        assertEquals("redirect:/workouts", viewName);
    }
}
