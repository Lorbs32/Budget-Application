package com.budget.app.domain;

	import java.util.*;
	import java.text.NumberFormat;
	import java.text.ParseException;

	// This class is now an Object Model inside DebtPayoffService
	class Debt {
	    String name;
	    double balance;
	    double minPayment;
	    double interestRate;
	    double totalInterestPaid = 0;

	    public Debt(String name, double balance, double minPayment, double interestRate) {
	        this.name = name;
	        this.balance = balance;
	        this.minPayment = minPayment;
	        this.interestRate = interestRate / 100; // Convert percentage to decimal
	    }

	    public double calculateMonthlyInterest() {
	        return balance * (interestRate / 12);
	    }

	    public Debt clone() {
	        return new Debt(this.name, this.balance, this.minPayment, this.interestRate * 100);
	    }
	}

	public class DebtPayoffSnowball {
	    public static void main(String[] args) {


			// This part is now collected via a form in the dashboard.html
			Scanner scanner = new Scanner(System.in);
	        scanner.useDelimiter("\n");
	        List<Debt> debts = new ArrayList<>();

	        // This part is now handled via the form and mapped to the Controller @Post
	        System.out.print("Enter number of debts: ");
	        int numDebts = Integer.parseInt(scanner.next().trim());

	        for (int i = 0; i < numDebts; i++) {
	            System.out.print("Enter name of debt " + (i + 1) + ": ");
	            String name = scanner.next().trim();

	            System.out.print("Enter balance of " + name + ": ");
	            double balance = readValidDouble(scanner);

	            System.out.print("Enter minimum payment for " + name + ": ");
	            double minPayment = readValidDouble(scanner);

	            System.out.print("Enter annual interest rate (as percentage, e.g., '5' or '5%') for " + name + ": ");
	            double interestRate = readValidDouble(scanner);

	            Debt debt = new Debt(name, balance, minPayment, interestRate);
	            debts.add(debt);
	        }

	        double originalTotalInterest = 0;
	        for (Debt debt : debts) {
	            double debtInterest = simulateMinimumPayments(debt);
	            originalTotalInterest += debtInterest;
	        }

	        List<Debt> snowballDebts = new ArrayList<>();
	        for (Debt d : debts) {
	            snowballDebts.add(d.clone());
	        }

	        snowballDebts.sort(Comparator.comparingDouble(d -> d.balance));

	        System.out.print("Enter extra amount to pay toward debt each month: ");
	        double extraPayment = readValidDouble(scanner);

	        int month = 0;
	        double totalSnowballInterestPaid = 0;

			// This part is now a method inside of DebtPayoffResult
	        while (!snowballDebts.isEmpty()) {
	            month++;
	            System.out.println("\nMonth " + month + " - Paying Off Debts");

	            List<Double> payments = new ArrayList<>();
	            for (int i = 0; i < snowballDebts.size(); i++) {
	                payments.add(0.0);
	            }

	            for (int i = 0; i < snowballDebts.size(); i++) {
	                Debt debt = snowballDebts.get(i);
	                double interest = debt.calculateMonthlyInterest();
	                debt.totalInterestPaid += interest;
	                debt.balance += interest;

	                double payment = Math.min(debt.minPayment, debt.balance);
	                debt.balance -= payment;
	                payments.set(i, payment);
	            }

	            double remainingExtra = extraPayment;
	            for (int i = 0; i < snowballDebts.size() && remainingExtra > 0; i++) {
	                Debt currentDebt = snowballDebts.get(i);
	                if (currentDebt.balance > 0) {
	                    double extraPay = Math.min(remainingExtra, currentDebt.balance);
	                    currentDebt.balance -= extraPay;
	                    remainingExtra -= extraPay;
	                    payments.set(i, payments.get(i) + extraPay);
	                }
	            }

	            for (int i = 0; i < snowballDebts.size(); i++) {
	                Debt debt = snowballDebts.get(i);
	                System.out.println("Paying $" + String.format("%.2f", payments.get(i)) +
	                        " towards " + debt.name +
	                        " (Remaining: $" + String.format("%.2f", Math.max(debt.balance, 0)) + ")");
	            }

	            Iterator<Debt> iterator = snowballDebts.iterator();
	            while (iterator.hasNext()) {
	                Debt debt = iterator.next();
	                if (debt.balance <= 0.01) {
	                    System.out.println("✅ " + debt.name + " is PAID OFF!");
	                    extraPayment += debt.minPayment;
	                    totalSnowballInterestPaid += debt.totalInterestPaid;
	                    iterator.remove();
	                }
	            }
	            snowballDebts.sort(Comparator.comparingDouble(d -> d.balance));
	        }

	        double interestSaved = originalTotalInterest - totalSnowballInterestPaid;

			// These outputs are now part of what is displayed by DebtPayoffResult on the dashboard
	        System.out.println("\n🎉 All debts are paid off in " + month + " months!");
	        System.out.println("\n📊 Interest Summary:");
	        System.out.printf("💰 Total interest paid with minimum payments: $%.2f%n", originalTotalInterest);
	        System.out.printf("💡 Total interest paid using Debt Snowball: $%.2f%n", totalSnowballInterestPaid);
	        System.out.printf("🎉 Money saved using Debt Snowball: $%.2f%n", interestSaved);

	        scanner.close();
	    }


	    public static double readValidDouble(Scanner scanner) {
	        while (true) {
	            try {
	                String input = scanner.next().replaceAll("[,\\s%]", "");
	                if (input.isEmpty()) {
	                    System.out.print("Invalid input. Please enter a valid number: ");
	                    continue;
	                }
	                return Double.parseDouble(input);
	            } catch (NumberFormatException e) {
	                System.out.print("Invalid input. Please enter a valid number: ");
	            }
	        }
	    }

	    // This part is now moved into the DebtPayoffService
	    public static double simulateMinimumPayments(Debt originalDebt) {
	        Debt debt = originalDebt.clone();
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
	                System.out.println("Warning: Minimum payment for " + debt.name +
	                        " is insufficient to cover monthly interest in minimum payment simulation. Debt may never be paid off.");
	                break;
	            }
	        }
	        if (months == maxMonths) {
	            System.out.println("Minimum payment simulation did not converge for " + debt.name);
	        }
	        return totalInterest;
	    }
	}




