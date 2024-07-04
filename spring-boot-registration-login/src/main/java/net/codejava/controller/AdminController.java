package net.codejava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import net.codejava.model.User;
import net.codejava.repository.UserRepository;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/users")
    public String listUsers(Model model) {
        // Retrieve the list of users from the repository
        List<User> listUsers = userRepo.findAll();
        // Add the list of users to the model
        model.addAttribute("listUsers", listUsers);
        // Return the view name
        return "users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
        // Delete the user with the given ID from the repository
        userRepo.deleteById(id);
        // Redirect to the list of users page
        return "redirect:/users";
    }
}
