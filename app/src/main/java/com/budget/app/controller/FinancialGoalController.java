package com.budget.app.controller;

import com.budget.app.entity.Budget;
import com.budget.app.entity.FinancialGoal;
import com.budget.app.service.BudgetService;
import com.budget.app.service.financialGoal.FinancialGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/financialgoals")
public class FinancialGoalController {
	@Autowired
	private BudgetService budgetService;
	@Autowired
	private FinancialGoalService financialGoalService;

	@GetMapping("/create")
	public String create(@RequestParam("financialGoalName") String goalName, @RequestParam("budgetId") int budgetId) {
		Budget budget = budgetService.getBudgetById(budgetId);

		FinancialGoal financialGoal = new FinancialGoal();
		financialGoal.setGoalName(goalName);
		financialGoal.setBudget(budget);

		financialGoalService.saveFinancialGoal(financialGoal);

		return "redirect:/dashboard";
	}
}
