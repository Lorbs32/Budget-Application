package com.budget.app.service;

	import java.util.*;

	public class BudgetSummaryCalculator {
	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);
	        Map<String, Double> expenses = new LinkedHashMap<>();
	        String[] fixedCategories = {
	            "Giving", "Savings", "Housing", "Transportation", 
	            "Food", "Personal", "Lifestyle", "Health", "Insurance"
	        };

	        // Add fixed categories to the map
	        for (String category : fixedCategories) {
	            expenses.put(category, 0.0);
	        }

	        // Income input
	        System.out.println("Enter your income sources (type 'done' to finish):");
	        double totalIncome = 0;
	        while (true) {
	            System.out.print("Income amount: ");
	            String input = scanner.nextLine();
	            if (input.equalsIgnoreCase("done")) break;
	            try {
	                double income = Double.parseDouble(input);
	                totalIncome += income;
	            } catch (NumberFormatException e) {
	                System.out.println("Please enter a valid number.");
	            }
	        }

	        // Show category preview before expense input
	        System.out.println("\nYou will now be asked to enter your expenses for each of the following categories:");
	        for (String category : fixedCategories) {
	            System.out.println(" - " + category);
	        }
	        System.out.println("Type 0 if there’s no expense for a category.\n");

	        // Expenses for fixed categories
	        for (String category : fixedCategories) {
	            System.out.printf("%s: ", category);
	            String input = scanner.nextLine();
	            try {
	                double amount = Double.parseDouble(input);
	                if (amount >= 0) {
	                    expenses.put(category, amount);
	                } else {
	                    System.out.println("Amount cannot be negative. Skipping this category.");
	                }
	            } catch (NumberFormatException e) {
	                System.out.println("Invalid input. Skipping " + category + ".");
	            }
	        }

	        // Custom "Other" categories
	        while (true) {
	            System.out.print("\nDo you have any other (miscellaneous) expenses? (yes/no): ");
	            String response = scanner.nextLine().trim().toLowerCase();
	            if (response.equals("no")) {
	                break;
	            } else if (response.equals("yes")) {
	                System.out.print("Enter custom category name: ");
	                String customCategory = scanner.nextLine().trim();

	                if (customCategory.isEmpty()) {
	                    System.out.println("Category name cannot be empty.");
	                    continue;
	                }

	                System.out.print("Amount: ");
	                String amountStr = scanner.nextLine();
	                try {
	                    double amount = Double.parseDouble(amountStr);
	                    if (amount < 0) {
	                        System.out.println("Amount cannot be negative.");
	                        continue;
	                    }
	                    expenses.put(customCategory, amount);
	                } catch (NumberFormatException e) {
	                    System.out.println("Invalid amount. Skipping custom category.");
	                }
	            } else {
	                System.out.println("Please answer 'yes' or 'no'.");
	            }
	        }

	        // Total expenses & balance
	        double totalExpenses = 0;
	        for (double value : expenses.values()) {
	            totalExpenses += value;
	        }
	        double remainingBalance = totalIncome - totalExpenses;

	        // Summary
	        System.out.println("\n------------------------------");
	        System.out.printf("TOTAL INCOME:      $%.2f\n", totalIncome);
	        System.out.printf("TOTAL EXPENSES:    $%.2f\n", totalExpenses);
	        System.out.printf("REMAINING BALANCE: $%.2f\n", remainingBalance);
	        System.out.println("\nCATEGORY         | AMOUNT");
	        System.out.println("-----------------|-----------");
	        for (Map.Entry<String, Double> entry : expenses.entrySet()) {
	            System.out.printf("%-16s | $%.2f\n", entry.getKey(), entry.getValue());
	        }
	    }
	}



