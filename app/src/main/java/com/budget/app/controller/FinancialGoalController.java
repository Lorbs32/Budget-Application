package com.budget.app.controller;

import com.budget.app.entity.BudgetDate;
import com.budget.app.entity.FinancialGoal;
import com.budget.app.service.BudgetService;
import com.budget.app.service.financialGoal.FinancialGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/financial-goals")
public class FinancialGoalController {
	@Autowired
	private FinancialGoalService financialGoalService;
	@Autowired
	private BudgetService budgetService;

	@GetMapping("/page")
	public String financialGoals(Model model, @RequestParam("budgetDateId") int budgetDateId) {
		model.addAttribute("financialGoals", financialGoalService.getFinancialGoalsByBudgetDateId(budgetDateId));
		model.addAttribute("budgetDateId", budgetDateId);

		return "financialGoals";
	}

	@RequestMapping("/create")
	public String create(@RequestParam("financialGoalName") String goalName, @RequestParam("budgetDateId") int budgetDateId) {
		FinancialGoal financialGoal = new FinancialGoal();
		BudgetDate budgetDate = budgetService.getBudgetDateById(budgetDateId);

		financialGoal.setGoalName(goalName);
		financialGoal.setBudgetDate(budgetDate);

		financialGoalService.saveFinancialGoal(financialGoal);

		return "redirect:/financial-goals/page?budgetDateId=" + budgetDateId;
	}

	@RequestMapping("/delete")
	public String delete(@RequestParam("financialGoalId") int financialGoalId, @RequestParam("budgetDateId") int budgetDateId) {
		financialGoalService.deleteFinancialGoalById(financialGoalId);
		return "redirect:/financial-goals/page?budgetDateId=" + budgetDateId;
	}
}
