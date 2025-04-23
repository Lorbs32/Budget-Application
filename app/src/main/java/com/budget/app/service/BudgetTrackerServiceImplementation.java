package com.budget.app.service;

import com.budget.app.entity.*;
import com.budget.app.entity.RecurrenceType;
import com.budget.app.security.model.CustomUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Transactional
public class BudgetTrackerServiceImplementation implements BudgetTrackerService {

    @Autowired
	private BudgetService budgetService;

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
	
	private BigDecimal bigDecimalZero = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
	
	private BigDecimal calculateBalance(BudgetDate budgetDateSelected) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		User currentUser = userDetails.getUser();
		
		BigDecimal balance = bigDecimalZero;
		try
		{				
			Budget budget = budgetService.getBudget(currentUser.getId(), budgetDateSelected.getId());
			if (budget == null) {
				System.out.println("No budget found for user " + currentUser.getId() + " and budgetDateId " + budgetDateSelected.getId());
				return null;
			}

			List<Category> categories = budgetService.getCategories(budget.getId());


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

			
			List<LineItem>  filteredLineItems = budgetService.getLineItemsByCategoryIds(categories);

			BigDecimal totalMonthlyIncome = bigDecimalZero;
			BigDecimal totalMonthlyExpenses = bigDecimalZero;
			BigDecimal relatedTransactionsTotal = bigDecimalZero;
			
			// On each line item get related transactions
			// If no transactions then set actual amount to 0
			// If some transactions then initialize accumulated actual amount to 0
			// Then loop through list of transactions and add to the accumulated actual amount
			// At end of loop set actual amount to accumulated actual amount
			for (LineItem item : filteredLineItems)
			{
				BigDecimal currTotal = item.getPlannedAmount();
				List<Transaction> relatedTransactions = budgetService.getTransactionsByLineItemId(item);
				if (!relatedTransactions.isEmpty())
				{
					for (Transaction transact : relatedTransactions)
					{
						relatedTransactionsTotal = relatedTransactionsTotal.add(transact.getActualAmount());
					}
				}
				
				BigDecimal weeklyMultiplier = BigDecimal.valueOf(4.0);
				BigDecimal biWeeklyMultiplier = BigDecimal.valueOf(2.0);
				BigDecimal yearlyDivisor = BigDecimal.valueOf(12);

				RecurrenceType recType = item.getRecurrenceType();
				
				if(recType.equals(RecurrenceType.WEEKLY)) {
					currTotal = currTotal.multiply(weeklyMultiplier);
				} else if(recType.equals(RecurrenceType.BI_WEEKLY)) {
					currTotal = currTotal.multiply(biWeeklyMultiplier);					
				} else if(recType.equals(RecurrenceType.YEARLY)) {
					currTotal = currTotal.divide(yearlyDivisor, 2, RoundingMode.HALF_UP);
				}
				
				
				// This looks at planned amount only...need to determine if this should be adjust to
				// address some combination of planned amount and actual transaction
				if(item.isIncome()) {
					totalMonthlyIncome = totalMonthlyIncome.add(currTotal);					
				} else {
					totalMonthlyExpenses = totalMonthlyExpenses.add(currTotal);										
				}				
			}

			balance = totalMonthlyIncome.subtract(totalMonthlyExpenses);

		}		
		catch (Exception e)
		{
			e.printStackTrace();
			return balance;
		}
		
		return balance;
	}
	
    @Override
    public List<BudgetTracker> getBudgetTrackerForBudgetDates(List<BudgetDate> budgetDates) {
        List<BudgetTracker> budgetTrackers = new ArrayList<>();
                
        for (int i=0; i < budgetDates.size(); i++) {
        	int budgetDateId = budgetDates.get(i).getId();
        	BigDecimal balance = bigDecimalZero;
    		BudgetTracker budgetTracker = new BudgetTracker();
        	BudgetDate budgetDateSelected = budgetService.getBudgetDateById(budgetDateId);
        	budgetTracker.setBudgetDate(budgetDateSelected);
        	balance = calculateBalance(budgetDateSelected);
        	
        	// If there is no budget for given month, do not display
        	// any tracker info for that month
        	if (balance == null) {
        		balance = bigDecimalZero;
    			budgetTracker.setBudgetText("&nbsp;");
    			budgetTracker.setBudgetTextClass("budget");        		
        	} else {
    			budgetTracker.setBudgetText("&#x2713; It's an EveryDollar Budget!");
    			budgetTracker.setBudgetTextClass("budget on_budget");
        		if(balance.compareTo(bigDecimalZero) < 0) {
        			budgetTracker.setBudgetText("<strong>$" + balance.abs().toString() + "</strong> over budget");
        			budgetTracker.setBudgetTextClass("budget over_budget");
        		} else if(balance.compareTo(bigDecimalZero) < 0) {
        			budgetTracker.setBudgetText("<strong>$" + balance.toString() + "</strong> left to budget");
        			budgetTracker.setBudgetTextClass("budget under_budget");
        		}        		
        	}
    		budgetTracker.setBalance(balance);
    		budgetTrackers.add(budgetTracker);
        }
        return budgetTrackers;
    }

    
    @Override
    public List<BudgetTracker> getBudgetTrackerForYear(int year) {
        List<BudgetTracker> budgetTrackers = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            LocalDate date = LocalDate.of(year, month, 1);
        }
        return budgetTrackers;
    }
}

