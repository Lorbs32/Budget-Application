package com.budget.app.service;

import com.budget.app.entity.*;
import com.budget.app.repository.BudgetDateRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BudgetDateServiceImplementation implements BudgetDateService {
    @Autowired
    private BudgetDateRepository budgetDateRepository;

    @Override
    public BudgetDate budgetForMonth(LocalDate date) {
        return budgetDateRepository.findByBudgetDateBetween(date);
    }

    @Override
    public BudgetDate budgetForPreviousMonth(LocalDate date) {
        // Calculate previous month's date
        LocalDate previousMonth = date.minusMonths(1);
        return budgetDateRepository.findByBudgetDateBetween(previousMonth);
    }

    @Override
    public List<BudgetDate> budgetsForNextMonths(int n) {
        List<BudgetDate> budgets = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            LocalDate nextMonth = LocalDate.now().plusMonths(i);
            // Add BudgetDate for the next month
            budgets.add(budgetDateRepository.findByBudgetDateBetween(nextMonth));
        }
        return budgets;
    }

    @Override
    public List<BudgetDate> budgetsForDateRange(LocalDate startDate, LocalDate endDate) {
        List<BudgetDate> budgets = new ArrayList<>();
        LocalDate currentMonth = startDate;
        // Loop through months within date range
        while (!currentMonth.isAfter(endDate)) {
            budgets.add(budgetDateRepository.findByBudgetDateBetween(currentMonth));
            currentMonth = currentMonth.plusMonths(1);
        }
        return budgets;
    }

    @Override
    public BudgetDate budgetForDateRange(LocalDate startDate) {
//        BudgetDate budget;
//        LocalDate selectedMonth = startDate;
        BudgetDate budget = budgetDateRepository.findByStartDate(startDate);

//        while (!selectedMonth.isAfter(endDate)) {
//            budget.add(budgetDateRepository.findByBudgetDateBetween(currentMonth));
//            currentMonth = currentMonth.plusMonths(1);
//        }
        return budget;
    }

    @Override
    public List<BudgetDate> budgetsForPreviousMonths(int n) {
        List<BudgetDate> budgets = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            LocalDate previousMonth = LocalDate.now().minusMonths(i);
            // Add BudgetDate for the previous month
            budgets.add(budgetDateRepository.findByBudgetDateBetween(previousMonth));
        }
        return budgets;
    }

    @Override
    public BudgetDate budgetForCurrentMonth() {
        // Get the current month's budget
        return budgetDateRepository.findByBudgetDateBetween(LocalDate.now());
    }

    @Override
    public List<BudgetDate> budgetsForYear(int year) {
        List<BudgetDate> budgets = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            LocalDate date = LocalDate.of(year, month, 1);
            // Add the BudgetDate for each month in the year
            budgets.add(budgetDateRepository.findByBudgetDateBetween(date));
        }
        return budgets;
    }
}

