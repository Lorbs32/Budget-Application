package com.budget.app.domain;

import java.util.ArrayList;
import java.util.List;

public class DebtPayoffInput {

    private List<DebtInput> debts = new ArrayList<>();
    private double extraPayment;

    public List<DebtInput> getDebts() {
        return debts;
    }

    public void setDebts(List<DebtInput> debts) {
        this.debts = debts;
    }

    public double getExtraPayment() {
        return extraPayment;
    }

    public void setExtraPayment(double extraPayment) {
        this.extraPayment = extraPayment;
    }
}
