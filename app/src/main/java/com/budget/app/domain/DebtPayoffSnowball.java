package com.budget.app.domain;

	import java.util.*;
	import java.text.NumberFormat;
	import java.text.ParseException;

	class Debt {
	    String name;
	    double balance;
	    double minPayment;
	    double interestRate; // Annual interest rate as a decimal (e.g., 0.05 for 5%)
	    double totalInterestPaid = 0; // Track interest paid for this debt

	    public Debt(String name, double balance, double minPayment, double interestRate) {
	        this.name = name;
	        this.balance = balance;
	        this.minPayment = minPayment;
	        this.interestRate = interestRate / 100; // Convert percentage to decimal
	    }

	    // Method to calculate interest for one month
	    public double calculateMonthlyInterest() {
	        return balance * (interestRate / 12);
	    }
	    
	    // Clone method so we can simulate scenarios independently.
	    public Debt clone() {
	        // When cloning, we pass the interest rate as a percentage.
	        return new Debt(this.name, this.balance, this.minPayment, this.interestRate * 100);
	    }
	}

	public class DebtPayoffSnowball {
	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);
	        scanner.useDelimiter("\n"); // Ensure full lines are read properly
	        List<Debt> debts = new ArrayList<>();

	        // Getting user input for debts
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
	            double interestRate = readValidDouble(scanner); // Handles inputs like 5, 5%, etc.

	            Debt debt = new Debt(name, balance, minPayment, interestRate);
	            debts.add(debt);
	        }
	        
	        // Simulate the minimum–payment only scenario for each debt.
	        double originalTotalInterest = 0;
	        for (Debt debt : debts) {
	            double debtInterest = simulateMinimumPayments(debt);
	            originalTotalInterest += debtInterest;
	        }

	        // For the Debt Snowball simulation, create a deep copy of the debts list.
	        List<Debt> snowballDebts = new ArrayList<>();
	        for (Debt d : debts) {
	            snowballDebts.add(d.clone());
	        }
	        
	        // Sort debts from smallest balance to largest.
	        snowballDebts.sort(Comparator.comparingDouble(d -> d.balance));

	        System.out.print("Enter extra amount to pay toward debt each month: ");
	        double extraPayment = readValidDouble(scanner);

	        int month = 0;
	        double totalSnowballInterestPaid = 0;

	        // Debt Snowball Simulation: loop month-by-month until all debts are paid.
	        while (!snowballDebts.isEmpty()) {
	            month++;
	            System.out.println("\nMonth " + month + " - Paying Off Debts");

	            // Prepare a list to track payments for display purposes.
	            List<Double> payments = new ArrayList<>();
	            for (int i = 0; i < snowballDebts.size(); i++) {
	                payments.add(0.0);
	            }

	            // First, for each debt, add interest and subtract the minimum payment.
	            for (int i = 0; i < snowballDebts.size(); i++) {
	                Debt debt = snowballDebts.get(i);
	                double interest = debt.calculateMonthlyInterest();
	                debt.totalInterestPaid += interest;
	                debt.balance += interest;

	                double payment = Math.min(debt.minPayment, debt.balance); // Prevent overpayment.
	                debt.balance -= payment;
	                payments.set(i, payment);
	            }

	            // Now, apply the extra payment across debts in order (cascade extra funds).
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

	            // Display payments for this month.
	            for (int i = 0; i < snowballDebts.size(); i++) {
	                Debt debt = snowballDebts.get(i);
	                System.out.println("Paying $" + String.format("%.2f", payments.get(i)) +
	                        " towards " + debt.name +
	                        " (Remaining: $" + String.format("%.2f", Math.max(debt.balance, 0)) + ")");
	            }

	            // Check and remove debts that are paid off.
	            Iterator<Debt> iterator = snowballDebts.iterator();
	            while (iterator.hasNext()) {
	                Debt debt = iterator.next();
	                if (debt.balance <= 0.01) { // Consider debt paid off if nearly zero.
	                    System.out.println("✅ " + debt.name + " is PAID OFF!");
	                    // Add the freed-up minimum payment to the extra funds for subsequent months.
	                    extraPayment += debt.minPayment;
	                    totalSnowballInterestPaid += debt.totalInterestPaid;
	                    iterator.remove();
	                }
	            }
	            
	            // Resort the list so that the smallest remaining balance is first.
	            snowballDebts.sort(Comparator.comparingDouble(d -> d.balance));
	        }

	        // Calculate interest difference.
	        double interestSaved = originalTotalInterest - totalSnowballInterestPaid;

	        System.out.println("\n🎉 All debts are paid off in " + month + " months!");
	        System.out.println("\n📊 Interest Summary:");
	        System.out.printf("💰 Total interest paid with minimum payments: $%.2f%n", originalTotalInterest);
	        System.out.printf("💡 Total interest paid using Debt Snowball: $%.2f%n", totalSnowballInterestPaid);
	        System.out.printf("🎉 Money saved using Debt Snowball: $%.2f%n", interestSaved);

	        scanner.close();
	    }

	    // Enhanced input validation:
	    // This method removes commas, spaces, and percent signs before parsing the number.
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
	                // Clear the invalid input if needed.
	            }
	        }
	    }

	    // Simulate the minimum-payment only scenario for a single debt.
	    // It runs month-by-month until the debt is paid off or a maximum number of months is reached.
	    public static double simulateMinimumPayments(Debt originalDebt) {
	        // Clone the debt so we don't modify the original.
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
	            // If the minimum payment does not cover the interest, warn and break.
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




