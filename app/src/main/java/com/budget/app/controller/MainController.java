package com.budget.app.controller;

import com.budget.app.entity.User;
import com.budget.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MainController
{
    @Autowired
    UserService userService;

    @Autowired
    User authUser = null;

    @RequestMapping("/")
    public String home(Model model)
    {
        List<User> users = userService.getUsers();
        model.addAttribute("users",users);
        model.addAttribute("text", "Current Users");
        return "login";
    }

    @RequestMapping("/dashboard")
    public String dashboard(Model model, @ModelAttribute("email") String userEmail, BindingResult result)
    {
        if(userService.getUserByEmail(userEmail) == null)
        {
            System.out.println("dashboard user email doesn't exist or no parameter included.");
            return "redirect:/";
        }
        authUser = userService.getUserByEmail(userEmail);
        model.addAttribute("user",authUser);
        return "dashboard";
    }
}