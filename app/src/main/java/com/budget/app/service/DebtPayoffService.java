package com.budget.app.service;

import com.budget.app.domain.DebtInput;
import com.budget.app.domain.DebtPayoffResult;
import com.budget.app.domain.DebtPayoffInput;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Service
public class DebtPayoffService {

    public DebtPayoffResult calculatePayoff(List<DebtInput> debtInputs, double extraPayment) {
        List<Debt> originalDebts = new ArrayList<>();
        for (DebtInput input : debtInputs) {
            originalDebts.add(new Debt(
                    input.getName(),
                    input.getBalance(),
                    input.getMinPayment(),
                    input.getInterestRate()
            ));
        }

        // Minimum payments
        double originalTotalInterest = 0;
        for (Debt debt : originalDebts) {
            originalTotalInterest += simulateMinimumPayments(debt.clone());
        }

        // Snowball strategy
        List<Debt> snowballDebts = new ArrayList<>();
        for (Debt d : originalDebts) {
            snowballDebts.add(d.clone());
        }

        snowballDebts.sort(Comparator.comparingDouble(d -> d.balance));

        int month = 0;
        double totalSnowballInterestPaid = 0;

        while (!snowballDebts.isEmpty()) {
            month++;

            // Add interest and apply minimum payments
            for (Debt debt : snowballDebts) {
                double interest = debt.calculateMonthlyInterest();
                debt.totalInterestPaid += interest;
                debt.balance += interest;

                double payment = Math.min(debt.minPayment, debt.balance);
                debt.balance -= payment;
            }

            // Apply extra payment
            double remainingExtra = extraPayment;
            for (Debt debt : snowballDebts) {
                if (debt.balance > 0 && remainingExtra > 0) {
                    double extraPay = Math.min(remainingExtra, debt.balance);
                    debt.balance -= extraPay;
                    remainingExtra -= extraPay;
                }
            }

            // Remove paid-off debts and add freed-up payments
            Iterator<Debt> iterator = snowballDebts.iterator();
            while (iterator.hasNext()) {
                Debt debt = iterator.next();
                if (debt.balance <= 0.01) {
                    extraPayment += debt.minPayment;
                    totalSnowballInterestPaid += debt.totalInterestPaid;
                    iterator.remove();
                }
            }

            snowballDebts.sort(Comparator.comparingDouble(d -> d.balance));
        }

        double interestSaved = originalTotalInterest - totalSnowballInterestPaid;

        return new DebtPayoffResult(month, originalTotalInterest, totalSnowballInterestPaid, interestSaved);
    }

    public DebtPayoffResult calculate(DebtPayoffInput input) {
        return calculatePayoff(input.getDebts(), input.getExtraPayment());
    }

    private double simulateMinimumPayments(Debt debt) {
        double totalInterest = 0;
        int months = 0;
        int maxMonths = 10000;

        while (debt.balance > 0.01 && months < maxMonths) {
            double interest = debt.balance * (debt.interestRate / 12);
            totalInterest += interest;
            debt.balance += interest;
            double payment = Math.min(debt.minPayment, debt.balance);
            debt.balance -= payment;
            months++;

            if (payment <= interest) {
                break; // Infinite loop risk
            }
        }

        return totalInterest;
    }

    // Debt model object
    private static class Debt {
        String name;
        double balance;
        double minPayment;
        double interestRate;
        double totalInterestPaid = 0;

        public Debt(String name, double balance, double minPayment, double interestRate) {
            this.name = name;
            this.balance = balance;
            this.minPayment = minPayment;
            this.interestRate = interestRate / 100;
        }

        public double calculateMonthlyInterest() {
            return balance * (interestRate / 12);
        }

        public Debt clone() {
            return new Debt(this.name, this.balance, this.minPayment, this.interestRate * 100);
        }
    }
}
