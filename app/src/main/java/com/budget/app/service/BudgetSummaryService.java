package com.budget.app.service;

import com.budget.app.domain.BudgetSummaryInput;
import com.budget.app.domain.BudgetSummaryResult;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BudgetSummaryService {

    public BudgetSummaryResult calculateSummary(BudgetSummaryInput input) {
        double totalIncome = input.getIncomes().stream()
                .filter(i -> i != null)
                .mapToDouble(Double::doubleValue)
                .sum();

        Map<String, Double> expenses = input.getExpenses();

        double totalExpenses = expenses.values().stream()
                .filter(e -> e != null)
                .mapToDouble(Double::doubleValue)
                .sum();

        double remainingBalance = totalIncome - totalExpenses;

        return new BudgetSummaryResult(totalIncome, totalExpenses, remainingBalance, expenses);
    }
}
