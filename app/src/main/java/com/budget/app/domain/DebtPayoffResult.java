package com.budget.app.domain;

public class DebtPayoffResult {
    private int monthsToPayOff;
    private double totalInterestMinimum;
    private double totalInterestSnowball;
    private double interestSaved;

    public DebtPayoffResult(int monthsToPayOff, double totalInterestMinimum, double totalInterestSnowball, double interestSaved) {
        this.monthsToPayOff = monthsToPayOff;
        this.totalInterestMinimum = totalInterestMinimum;
        this.totalInterestSnowball = totalInterestSnowball;
        this.interestSaved = interestSaved;
    }

    public int getMonthsToPayOff() {
        return monthsToPayOff;
    }

    public double getTotalInterestMinimum() {
        return totalInterestMinimum;
    }

    public double getTotalInterestSnowball() {
        return totalInterestSnowball;
    }

    public double getInterestSaved() {
        return interestSaved;
    }
}
