package com.budget.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/financialgoals")
public class FinancialGoalController {
	@GetMapping("/goals")
	public String goals(Model model) {
		return "financialgoals";
	}
}
