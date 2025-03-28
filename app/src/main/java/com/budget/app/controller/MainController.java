package com.budget.app.controller;

import com.budget.app.entity.*;
import com.budget.app.security.model.CustomUserDetails;
import com.budget.app.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
	@Autowired
	private BudgetService budgetService;

	@Autowired
	public LocalDate todaysDate;

	@Autowired
	public DateTimeFormatter customFormatter;

	@RequestMapping("/dashboard")
	public String dashboard(Model model, final Transaction transaction)
	{
		model.addAttribute("users", budgetService.getUsers());

		List<BudgetDate> budgetDates = budgetService.getBudgetDatesBetween(todaysDate);
		model.addAttribute("budgetDates", budgetDates);
		String temp = todaysDate.format(customFormatter);
		Date formattedDate = Date.valueOf(temp);
		transaction.setTransactionDate(formattedDate);

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

		List<Category> categories = budgetService.getCategories(budget.getId());
		model.addAttribute("categories", categories);

		List<LineItem> lineItems = budgetService.getLineItemsByCategoryIds(categories);
		model.addAttribute("lineItems", lineItems);

		List<Transaction> transactions = budgetService.getTransactions(lineItems);
		model.addAttribute("transactions", transactions);

		// On each line item get related transactions
		// If no transactions then set actual amount to 0
		// If some transactions then initialize accumulated actual amount to 0
		// Then loop through list of transactions and add to the accumulated actual amount
		// At end of loop set actual amount to accumulated actual amount
		for(LineItem item : lineItems)
		{
			List<Transaction> relatedTransactions = budgetService.getTransactionsByLineItemId(item);
			if(relatedTransactions.isEmpty())
			{
				item.setCumulativeActualAmount(BigDecimal.valueOf(0.00));
			}
			else
			{
				BigDecimal accumulatedActualAmount = BigDecimal.valueOf(0.00);
				for(Transaction transact : relatedTransactions)
				{
					accumulatedActualAmount = accumulatedActualAmount.add(transact.getActualAmount());
				}
				item.setCumulativeActualAmount(accumulatedActualAmount);
			}
		}
		List<String> labels = new ArrayList<>();
		List<Double> values = new ArrayList<>();

		for (LineItem lineItem : lineItems) {
			labels.add(lineItem.getLineItemName());

			double totalAmount = 0;
			for (Transaction tr : lineItem.getTransactions()) {
				totalAmount += tr.getActualAmount().doubleValue();
			}
			values.add(totalAmount);
		}

		// Add the pie chart data to the model
		model.addAttribute("labels", labels);
		model.addAttribute("values", values);
		return "dashboard";
	}

@RequestMapping ("/addTransactionToBudget")
	public String addTransaction(@ModelAttribute Transaction transaction, Model model)
	{
		budgetService.updateOrInsert(transaction);
		return "redirect:dashboard";
	}
}