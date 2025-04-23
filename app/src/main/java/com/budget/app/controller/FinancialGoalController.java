package com.budget.app.controller;

import com.budget.app.entity.Budget;
import com.budget.app.entity.Category;
import com.budget.app.entity.FinancialGoal;
import com.budget.app.entity.User;
import com.budget.app.security.model.CustomUserDetails;
import com.budget.app.service.BudgetService;
import com.budget.app.service.CategoryService;
import com.budget.app.service.financialGoal.FinancialGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/financial-goals")
public class FinancialGoalController {
	@Autowired
	private BudgetService budgetService;
	@Autowired
	private FinancialGoalService financialGoalService;

	@GetMapping("/page")
	public String financialGoals(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		User currentUser = userDetails.getUser();

		Budget budget = budgetService.getBudgetById(currentUser.getId());

		model.addAttribute("budget", budget);
		model.addAttribute("financialGoals", financialGoalService.getFinancialGoalsByBudgetId(budget.getId()));

		return "financialGoals";
	}

	@RequestMapping("/create")
	public String create(@RequestParam("financialGoalName") String goalName, @RequestParam("budgetId") int budgetId) {
		Budget budget = budgetService.getBudgetById(budgetId);
		FinancialGoal financialGoal = new FinancialGoal();

		financialGoal.setGoalName(goalName);
		financialGoal.setBudget(budget);

		financialGoalService.saveFinancialGoal(financialGoal);

		return "redirect:/financial-goals/page";
	}

	@RequestMapping("/delete")
	public String delete(@RequestParam("financialGoalId") int financialGoalId) {
		financialGoalService.deleteFinancialGoalById(financialGoalId);
		return "redirect:/financial-goals/page";
	}
}
