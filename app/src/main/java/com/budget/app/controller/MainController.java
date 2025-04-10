package com.budget.app.controller;

import com.budget.app.entity.*;
import com.budget.app.security.model.CustomUserDetails;
import com.budget.app.service.BudgetService;
import com.budget.app.service.CategoryService;
import com.budget.app.service.LineItemService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.Month;
import java.util.stream.Collectors;

@Controller
public class MainController {
	@Autowired
	private BudgetService budgetService;

	@Autowired
	public LocalDate todaysDate;

	@Autowired
	public DateTimeFormatter customFormatter;

	@Autowired
	private LineItemService lineItemService;

	// Subscriptions pairing with Months helper method
	private String expandMonthAbbreviation(String shortMonth) {
		switch (shortMonth.toUpperCase()) {
			case "JAN": return "JANUARY";
			case "FEB": return "FEBRUARY";
			case "MAR": return "MARCH";
			case "APR": return "APRIL";
			case "MAY": return "MAY";
			case "JUN": return "JUNE";
			case "JUL": return "JULY";
			case "AUG": return "AUGUST";
			case "SEP": return "SEPTEMBER";
			case "OCT": return "OCTOBER";
			case "NOV": return "NOVEMBER";
			case "DEC": return "DECEMBER";
			default: throw new IllegalArgumentException("Invalid month abbreviation: " + shortMonth);
		}
	}

	@RequestMapping("/dashboard")
	public String dashboard(HttpServletRequest request, Model model, final Transaction transaction
	,@RequestParam(value = "budgetDateId", required = false, defaultValue = "0") int budgetDateId)
	{

		List<BudgetDate> budgetDates = null;
		if(budgetDateId != 0)
		{
			BudgetDate currentBudgetMonth = budgetService.getBudgetDateById(budgetDateId);
			budgetDates = budgetService.getBudgetDatesBetween(currentBudgetMonth.getStartDate());
			//System.out.println("CURRENT BUDGET MONTH: " + currentBudgetMonth.getBudgetMonth());
		}
		else
		{
			budgetDates = budgetService.getBudgetDatesBetween(todaysDate);
		}

		model.addAttribute("lineItem", new LineItem());
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

		try
		{
			Budget budget = budgetService.getBudget(currentUser.getId(), budgetDateSelected.getId());
			model.addAttribute("budget", budget);
			model.addAttribute("budgetId", budget.getId());

			List<Category> categories = budgetService.getCategories(budget.getId());
			model.addAttribute("categories", categories);


			List<LineItem> lineItems = budgetService.getLineItemsByCategoryIds(categories);
			// Convert budgetMonth (String) like "April" to numeric month
			String fullMonth = expandMonthAbbreviation(budgetDateSelected.getBudgetMonth());
			int monthValue = Month.valueOf(fullMonth).getValue();
			int yearValue = budgetDateSelected.getBudgetYear();

			// Extract the month from the selected budget date
			YearMonth targetMonth = YearMonth.of(yearValue, monthValue);

			// Filter line items based on recurrence
			List<LineItem> filteredLineItems = lineItems.stream()
					.filter(item -> lineItemService.isActiveForMonth(item, targetMonth))
					.collect(Collectors.toList());

			model.addAttribute("lineItems", filteredLineItems);

			// On each line item get related transactions
			// If no transactions then set actual amount to 0
			// If some transactions then initialize accumulated actual amount to 0
			// Then loop through list of transactions and add to the accumulated actual amount
			// At end of loop set actual amount to accumulated actual amount
			for (LineItem item : filteredLineItems)
			{
				List<Transaction> relatedTransactions = budgetService.getTransactionsByLineItemId(item);
				if (relatedTransactions.isEmpty())
				{
					item.setCumulativeActualAmount(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
				}
				else
				{
					BigDecimal accumulatedActualAmount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
					for (Transaction transact : relatedTransactions)
					{
						accumulatedActualAmount = accumulatedActualAmount.add(transact.getActualAmount());
					}
					item.setCumulativeActualAmount(accumulatedActualAmount);
				}
			}

			List<Transaction> transactions = budgetService.getTransactions(lineItems);
			model.addAttribute("transactions", transactions);

			List<String> labels = new ArrayList<>();
			List<Double> transactionValues = new ArrayList<>();
			List<Double> plannedValues = new ArrayList<>();
			List<Double> remainingValues = new ArrayList<>();

			for (LineItem lineItem : filteredLineItems)
			{
				double plannedAmount = lineItem.getPlannedAmount().doubleValue();

				double totalAmount = 0;
				for (Transaction tr : lineItem.getTransactions())
				{
					totalAmount += tr.getActualAmount().doubleValue();
				}

				double remainingAmount = plannedAmount - totalAmount;

				labels.add(lineItem.getLineItemName());
				transactionValues.add(totalAmount);
				plannedValues.add(plannedAmount);
				remainingValues.add(remainingAmount);
			}

			// Add the pie chart data to the model
			model.addAttribute("labels", labels);
			model.addAttribute("transactionValues", transactionValues);
			model.addAttribute("plannedValues", plannedValues);
			model.addAttribute("remainingValues", remainingValues);

			// Load normal page display
			model.addAttribute("isBudgetCreatedInCurrentMonth","YES");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			// Load blank state and add budget fragment
			model.addAttribute("isBudgetCreatedInCurrentMonth","NO");
			return "dashboard";
		}

		// Pre-populate transaction date to today's date in a format that the HTML field input type="date" can use.
		LocalDate todaysDate = LocalDate.now();
		transaction.setTransactionDate(todaysDate);

		return "dashboard";
	}

	@RequestMapping ("/addTransactionToBudget")
	public String addTransaction(@ModelAttribute Transaction transaction, Model model)
	{
		budgetService.updateOrInsert(transaction);
		return "redirect:dashboard";
	}
}