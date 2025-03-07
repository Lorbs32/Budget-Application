package com.budget.app.controller;

import com.budget.app.entity.*;
import com.budget.app.security.model.CustomUserDetails;
import com.budget.app.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

		List<BudgetDate> budgetDates = budgetService.getBudgetDatesBetween(todaysDate);
		model.addAttribute("budgetDates", budgetDates);

		BudgetDate budgetDateSelected = new BudgetDate();
        for (BudgetDate budgetDate : budgetDates)
		{
            if (budgetDate.getBudgetSelected())
			{
                budgetDateSelected = budgetDate;
            }
        }
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		User currentUser = userDetails.getUser();
		Budget budget = budgetService.getBudget(currentUser.getId(), budgetDateSelected.getId());
		model.addAttribute("budget", budget);

		List<Group> groups = budgetService.getGroups(budget.getId());
		model.addAttribute("groups", groups);

		List<LineItem> lineItems = budgetService.getLineItems(groups);
		model.addAttribute("lineItems", lineItems);

		List<Transaction> transactions = budgetService.getTransactions(lineItems);
		model.addAttribute("transactions", transactions);

		return "dashboard";
	}
}