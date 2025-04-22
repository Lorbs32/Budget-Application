package com.budget.app.service;

import com.budget.app.entity.BudgetDate;
import com.budget.app.entity.BudgetTracker;
import java.util.List;

public interface BudgetTrackerService {
    public List<BudgetTracker> getBudgetTrackerForBudgetDates(List<BudgetDate> budgetDates);
    public List<BudgetTracker> getBudgetTrackerForYear(int year);
}