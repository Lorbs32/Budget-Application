package com.budget.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/financialgoals")
public class FinancialGoalController {
	@PostMapping("/create")
	public String create() {
		return "";
	}
}
