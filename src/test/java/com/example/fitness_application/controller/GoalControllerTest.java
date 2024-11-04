package com.example.fitness_application.controller;

import com.example.fitness_application.model.dto.GoalDTO;
import com.example.fitness_application.service.GoalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GoalControllerTest {

    private static final Long TEST_ID = 1234L;

    @Mock
    private GoalService goalService;

    @Mock
    private Model model;

    @InjectMocks
    private GoalController goalController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllGoals() {
        String viewName = goalController.getAllGoals(model);

        verify(model).addAttribute("goals", goalService.getAllGoals());
        assertEquals("redirect:/start-page", viewName);
    }

    @Test
    void testShowGoalForm() {
        String viewName = goalController.showGoalForm(model);

        verify(model).addAttribute(eq("goal"), any(GoalDTO.class));
        assertEquals("goals-pages/goal-form", viewName);
    }

    @Test
    void testShowEditForm_WithExistingGoal() {
        GoalDTO goalDTO = new GoalDTO();
        when(goalService.getGoalById(TEST_ID)).thenReturn(goalDTO);

        String viewName = goalController.showEditForm(TEST_ID, model);

        verify(model).addAttribute("goal", goalDTO);
        assertEquals("goals-pages/goal-form", viewName);
    }

    @Test
    void testShowEditForm_WithNonExistingGoal() {
        when(goalService.getGoalById(TEST_ID)).thenReturn(null);

        String viewName = goalController.showEditForm(TEST_ID, model);

        assertEquals("redirect:/goals", viewName);
    }

    @Test
    void testSaveGoal() {
        GoalDTO goalDTO = new GoalDTO();

        String viewName = goalController.saveGoal(goalDTO);

        verify(goalService).saveOrUpdateGoal(goalDTO);
        assertEquals("redirect:/goals", viewName);
    }

    @Test
    void testDeleteGoal() {
        String viewName = goalController.deleteGoal(TEST_ID);

        verify(goalService).deleteGoal(TEST_ID);
        assertEquals("redirect:/goals", viewName);
    }
}
