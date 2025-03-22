package com.budget.app.service;

import com.budget.app.entity.*;

import java.time.LocalDate;
import java.util.List;

public interface BudgetService
{
    public List<User> getUsers();
    public User getUserByEmail(String email);
    public List<BudgetDate> getBudgetDatesBetween(LocalDate currentDate);
    public Budget getBudget(int userId, int dateId);
    public List<Category> getCategories(int budgetId);
    public List<LineItem> getLineItemsByCategoryIds(List<Category> categories);
    public List<Transaction> getTransactions(List<LineItem> lineItems);
    public void updateOrInsert(Transaction transaction);
    public List<Transaction> getTransactionsByLineItemId(LineItem item);

    // calculateBudgetSummary(Int budgetId);
}
