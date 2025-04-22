package com.budget.app.domain;

import java.util.Map;

public class BudgetSummaryResult {
    private double totalIncome;
    private double totalExpenses;
    private double remainingBalance;
    private Map<String, Double> categorizedExpenses;

    public BudgetSummaryResult() {}

    public BudgetSummaryResult(double totalIncome, double totalExpenses, double remainingBalance, Map<String, Double> categorizedExpenses) {
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
        this.remainingBalance = remainingBalance;
        this.categorizedExpenses = categorizedExpenses;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    public Map<String, Double> getCategorizedExpenses() {
        return categorizedExpenses;
    }

    public void setCategorizedExpenses(Map<String, Double> categorizedExpenses) {
        this.categorizedExpenses = categorizedExpenses;
    }
}
