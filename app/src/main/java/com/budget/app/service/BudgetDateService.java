package com.budget.app.service;

import com.budget.app.entity.BudgetDate;
import java.time.LocalDate;
import java.util.List;

public interface BudgetDateService {
    public BudgetDate budgetForMonth(LocalDate date);
    public BudgetDate budgetForCurrentMonth();
    public  BudgetDate budgetForPreviousMonth(LocalDate date);
    public List<BudgetDate> budgetsForNextMonths(int n);
    public List<BudgetDate> budgetsForDateRange(LocalDate startDate, LocalDate endDate);
    public BudgetDate budgetForDateRange(LocalDate startDate);
    public List<BudgetDate> budgetsForPreviousMonths(int n);
    public List<BudgetDate> budgetsForYear(int year);
}