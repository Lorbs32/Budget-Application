package com.budget.app.controller;

import com.budget.app.entity.User;
import com.budget.app.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class MainController {
	@Autowired
	private BudgetService budgetService;

	@Autowired
	public LocalDate todaysDate;

	@RequestMapping("/dashboard")
	public String dashboard(Model model)
	{
		model.addAttribute("users", budgetService.getUsers());
		model.addAttribute("budgetDates", budgetService.getBudgetDatesBetween(todaysDate));
		return "dashboard";
	}
}