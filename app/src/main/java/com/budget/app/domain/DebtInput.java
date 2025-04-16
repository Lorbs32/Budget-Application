package com.budget.app.domain;

public class DebtInput {
    private String name;
    private double balance;
    private double minPayment;
    private double interestRate;

    // Getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public double getMinPayment() {
        return minPayment;
    }
    public void setMinPayment(double minPayment) {
        this.minPayment = minPayment;
    }
    public double getInterestRate() {
        return interestRate;
    }
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}

