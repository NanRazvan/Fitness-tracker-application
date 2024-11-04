package com.example.fitness_application.controller;

import com.example.fitness_application.model.dto.GoalDTO;
import com.example.fitness_application.model.dto.UserDTO;
import com.example.fitness_application.service.GoalService;
import com.example.fitness_application.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private static final Long TEST_ID = 1234L;

    @Mock
    private UserService userService;

    @Mock
    private GoalService goalService;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testGetAllUsers() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");

        String viewName = userController.getAllUsers(model);

        verify(model).addAttribute("users", userService.getAllUsers());
        verify(model).addAttribute("loggedInUsername", "testUser");
        assertEquals("redirect:/start-page", viewName);
    }

    @Test
    void testShowUserWorkouts_WithValidGoal() {
        UserDTO userDTO = new UserDTO();
        GoalDTO goalDTO = new GoalDTO();
        goalDTO.setId(TEST_ID);
        userDTO.setGoal(goalDTO);

        when(userService.getUserById(TEST_ID)).thenReturn(userDTO);

        String viewName = userController.showUserWorkouts(TEST_ID, model);

        verify(model).addAttribute("goal", userDTO.getGoal());
        verify(model).addAttribute("workouts", userDTO.getGoal().getWorkouts());
        assertEquals("user-pages/user-workouts", viewName);
    }

    @Test
    void testShowUserWorkouts_NoGoal() {
        UserDTO userDTO = new UserDTO();

        when(userService.getUserById(TEST_ID)).thenReturn(userDTO);

        String viewName = userController.showUserWorkouts(TEST_ID, model);

        assertEquals("redirect:/users", viewName);
    }

    @Test
    void testShowUserForm() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");
        when(goalService.getAllGoals()).thenReturn(List.of(new GoalDTO()));

        String viewName = userController.showUserForm(model);

        verify(model).addAttribute(eq("user"), any(UserDTO.class));
        verify(model).addAttribute("goals", goalService.getAllGoals());
        assertEquals("user-pages/user-form", viewName);
    }

    @Test
    void testSaveUser() {
        UserDTO userDTO = new UserDTO();

        String viewName = userController.saveUser(userDTO);

        verify(userService).saveOrUpdateUser(userDTO);
        assertEquals("redirect:/start-page", viewName);
    }

    @Test
    void testDeleteUser() {
        String viewName = userController.deleteUser(TEST_ID);

        verify(userService).deleteUser(TEST_ID);
        assertEquals("redirect:/start-page", viewName);
    }
}
