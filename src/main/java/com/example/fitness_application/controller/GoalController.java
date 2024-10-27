package com.example.fitness_application.controller;

import com.example.fitness_application.model.dto.GoalDTO;
import com.example.fitness_application.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @GetMapping
    public String getAllGoals(Model model) {
        model.addAttribute("goals", goalService.getAllGoals());
        return "goals-pages/list-goals";
    }

    @GetMapping("/new")
    public String showGoalForm(Model model) {
        model.addAttribute("goal", new GoalDTO());
        return "goals-pages/goal-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        GoalDTO goalDTO = goalService.getGoalById(id);
        if (goalDTO != null) {
            model.addAttribute("goal", goalDTO);
            return "goals-pages/goal-form";
        } else {
            return "redirect:/goals";
        }
    }

    @PostMapping("/save")
    public String saveGoal(@ModelAttribute("goal") GoalDTO goalDTO) {
        goalService.saveOrUpdateGoal(goalDTO);
        return "redirect:/goals";
    }

    @GetMapping("/delete/{id}")
    public String deleteGoal(@PathVariable Long id) {
        goalService.deleteGoal(id);
        return "redirect:/goals";
    }
}
