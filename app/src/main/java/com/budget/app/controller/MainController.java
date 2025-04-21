package com.budget.app.controller;

import com.budget.app.domain.*;
import com.budget.app.entity.*;
import com.budget.app.entity.plaid.PlaidBankAccount;
import com.budget.app.security.model.CustomUserDetails;
import com.budget.app.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.time.Month;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController {

	@Autowired
	private BudgetService budgetService;

	@Autowired
	private BudgetTrackerService budgetTrackerService;

	@Autowired
	private DebtPayoffService debtPayoffService;

	@Autowired
	public LocalDate todaysDate;

	@Autowired
	public DateTimeFormatter customFormatter;

	@Autowired
	private LineItemService lineItemService;

	@Autowired
	private BudgetSummaryService budgetSummaryService;

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

	// Changed to GetMapping to use PostMapping below
	@GetMapping("/dashboard")
	public String dashboard(HttpServletRequest request, Model model, @ModelAttribute Transaction transaction
	,@RequestParam(value = "budgetDateId", required = false, defaultValue = "0") int budgetDateId)
	{
		System.out.println("🧪 Received budgetDateId from URL: " + budgetDateId);
		// Added transaction to model for PostMapping to not display nulls
		if (!model.containsAttribute("transaction")) {
			model.addAttribute("transaction", transaction);
		}

		List<BudgetDate> budgetDates = null;
		System.out.println("➡️ In GET /dashboard");
		if(budgetDateId != 0)
		{
			BudgetDate currentBudgetMonth = budgetService.getBudgetDateById(budgetDateId);
			budgetDates = budgetService.getBudgetDatesBetween(currentBudgetMonth.getStartDate());
			System.out.println("CURRENT BUDGET MONTH: " + currentBudgetMonth.getBudgetMonth());
		}
		else
		{
			budgetDates = budgetService.getBudgetDatesBetween(todaysDate);
		}

		model.addAttribute("lineItem", new LineItem());
		model.addAttribute("budgetDates", budgetDates);

		List<BudgetTracker> budgetTracker = null;
		budgetTracker = budgetTrackerService.getBudgetTrackerForBudgetDates(budgetDates);
		model.addAttribute("budgetTracker", budgetTracker);

		BudgetDate budgetDateSelected;

		if (budgetDateId != 0) {
			// Use the explicitly passed ID from the form or URL
			budgetDateSelected = budgetService.getBudgetDateById(budgetDateId);
			model.addAttribute("budgetDateSelected", budgetDateSelected);
		} else {
			// Fallback to selected budget logic if no ID was passed
			budgetDateSelected = new BudgetDate();
			for (BudgetDate budgetDate : budgetDates) {
				if (budgetDate.getBudgetSelected()) {
					budgetDateSelected = budgetDate;
				}
			}
		}
		model.addAttribute("budgetDateSelected", budgetDateSelected);
		System.out.println("Selected Budget Date: " + budgetDateSelected.getBudgetMonth() + " " + budgetDateSelected.getBudgetYear());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		User currentUser = userDetails.getUser();

		try
		{
			Budget budget = budgetService.getBudget(currentUser.getId(), budgetDateSelected.getId());
			if (budget == null) {
				System.out.println("No budget found for user " + currentUser.getId() + " and budgetDateId " + budgetDateSelected.getId());
				model.addAttribute("isBudgetCreatedInCurrentMonth", "NO");
				return "dashboard";
			}
			model.addAttribute("budget", budget);
			System.out.println("Budget attribute " + budget);
			model.addAttribute("budgetId", budget.getId());
			System.out.println("Budget ID independent attribute: " + budget.getId());
			model.addAttribute("budgetDateSelected", budgetDateSelected);

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
			List<LineItem> filteredMonthlyLineItems = lineItems.stream()
					.filter(item -> lineItemService.isActiveForMonth(item, targetMonth))
					.collect(Collectors.toList());

			model.addAttribute("filteredMonthlyLineItems", filteredMonthlyLineItems);

			List<LineItem>  filteredLineItems = budgetService.getLineItemsByCategoryIds(categories);
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

			List<PlaidBankAccount> banks = budgetService.getBanksByUserId(currentUser);
			model.addAttribute("banks", banks);

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
			// Added default values for the debt payoff form so Thymeleaf doesn't error
			model.addAttribute("debts", List.of(new DebtInput()));
			model.addAttribute("extraPayment", 0.0);
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
		System.out.println("✅ Final model budgetDateSelected ID: " + budgetDateSelected.getId());
		return "dashboard";
	}


	// Debt payoff calculator
	@PostMapping("/dashboard")
	public String postDashboardWithDebtPayoff(
			@RequestParam(name = "budgetDateId") Integer budgetDateId,
			@RequestParam(name = "name") List<String> names,
			@RequestParam(name = "balance") List<Double> balances,
			@RequestParam(name = "minPayment") List<Double> minPayments,
			@RequestParam(name = "interestRate") List<Double> interestRates,
			@RequestParam(name = "extraPayment") double extraPayment,
			Model model,
			HttpServletRequest request
	) {
		System.out.println("In POST /dashboard");
		System.out.println("POST /dashboard triggered with budgetDateId = " + budgetDateId);
		// 1. Build debt input list
		List<DebtInput> debts = new ArrayList<>();
		for (int i = 0; i < names.size(); i++) {
			DebtInput debt = new DebtInput();
			debt.setName(names.get(i));
			debt.setBalance(balances.get(i));
			debt.setMinPayment(minPayments.get(i));
			debt.setInterestRate(interestRates.get(i));
			debts.add(debt);
		}

		// 2. Calculate payoff results
		DebtPayoffResult result = debtPayoffService.calculatePayoff(debts, extraPayment);

		// 3. Add form results to the model
		model.addAttribute("debts", debts);
		model.addAttribute("extraPayment", extraPayment);
		model.addAttribute("debtPayoffResult", result);

		// 4. Reuse the GET controller to rebuild the dashboard
		System.out.println("✅ Re-rendering dashboard with debt result: " + result.getInterestSaved());
		return dashboard(request, model, new Transaction(), budgetDateId);
	}

	// Budget Summary
	@PostMapping("/dashboard/summary")
	public String handleBudgetSummaryForm(
			@RequestParam(name = "budgetDateId") int budgetDateId,
			@RequestParam(name = "incomes", required = false) List<Double> incomeSources,
			HttpServletRequest request,
			Model model
	) {
		System.out.println("🧪 POST received for summary. budgetDateId = " + budgetDateId);

		if (incomeSources == null) incomeSources = new ArrayList<>();

		Map<String, Double> expenses = new LinkedHashMap<>();
		for (String category : List.of("Giving", "Savings", "Housing", "Transportation", "Food", "Personal", "Lifestyle", "Health", "Insurance")) {
			String value = request.getParameter(category);
			if (value != null && !value.isBlank()) {
				try {
					expenses.put(category, Double.parseDouble(value));
				} catch (NumberFormatException ignored) {}
			}
		}

		BudgetSummaryInput input = new BudgetSummaryInput(incomeSources, expenses);
		BudgetSummaryResult result = budgetSummaryService.calculateSummary(input);

		model.addAttribute("budgetSummaryResult", result);

		return dashboard(request, model, new Transaction(), budgetDateId);
	}


	@RequestMapping ("/addTransactionToBudget")
	public String addTransaction(@ModelAttribute Transaction transaction, Model model,
								 HttpServletRequest request)
	{
		budgetService.updateOrInsert(transaction);

		// All requests that redirect to the dashboard need to retrieve the currently selected budget date ID and pass it through.
		String referrer = request.getHeader("referer");
		String budgetDateId = ExtractParameter.getParameterValue(referrer, "budgetDateId");

		return "redirect:dashboard?budgetDateId=" + budgetDateId;
	}
}