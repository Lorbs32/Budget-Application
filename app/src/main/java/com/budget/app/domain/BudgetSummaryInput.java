package com.budget.app.domain;

import java.util.List;
import java.util.Map;

public class BudgetSummaryInput {
    private List<Double> incomes;
    private Map<String, Double> expenses;

    public BudgetSummaryInput() {}

    public BudgetSummaryInput(List<Double> incomes, Map<String, Double> expenses) {
        this.incomes = incomes;
        this.expenses = expenses;
    }

    public List<Double> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<Double> incomes) {
        this.incomes = incomes;
    }

    public Map<String, Double> getExpenses() {
        return expenses;
    }

    public void setExpenses(Map<String, Double> expenses) {
        this.expenses = expenses;
    }
}
