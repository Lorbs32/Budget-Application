package com.budget.app.controller;

import com.budget.app.entity.Budget;
import com.budget.app.entity.FinancialGoal;
import com.budget.app.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/financialgoals")
public class FinancialGoalController {
	@Autowired
	private BudgetService budgetService;

//	@GetMapping("/create")
//	public String financialGoals(Model model) {
//		return "dashboard";
//	}

	@PostMapping("/create")
	public String create(@RequestParam("financialGoalName") String goalName, @RequestParam("budgetId") int budgetId) {
		Budget budget = budgetService.getBudgetById(budgetId);

		FinancialGoal financialGoal = new FinancialGoal();
		financialGoal.setGoalName(goalName);
		financialGoal.setBudget(budget);

		System.out.println(financialGoal.getGoalName());
		return "redirect:/dashboard";
	}
}
