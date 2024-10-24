package com.example.fitness_application.controller;

import com.example.fitness_application.model.dto.UserDTO;
import com.example.fitness_application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String getAllUsers(Model model) {
        List<UserDTO> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-pages/list-users";
    }

    @GetMapping("/new")
    public String showUserForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "user-pages/user-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        UserDTO userDTO = userService.getUserById(id);
        if (userDTO != null) {
            model.addAttribute("user", userDTO);
            return "user-pages/user-form";
        } else {
            return "redirect:/users";
        }
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") UserDTO userDTO) {
        userService.saveOrUpdateUser(userDTO);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
