package com.budget.app.service;

import com.budget.app.entity.*;
import com.budget.app.entity.plaid.PlaidAccessTokens;
import com.budget.app.entity.plaid.PlaidBankAccount;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    public Budget getBudgetById(int budgetId);
    public List<Budget> getAllBudgets();
    public BudgetDate getBudgetDateById(int budgetDateId);
    public Map<LineItem, LineItem> addBudgetBasedOnLastMonth(User currentUser, Budget lastMonthBudget, List<Category> categories, List<LineItem> lineItems, BudgetDate newBudgetDate);
    public Budget getBudgetByBudgetDateAndUser(User currentUser, BudgetDate budgetDate);
    public Budget getBudgetByBudgetId(int budgetId);
    public void addBudgetPredetermined(User currentUser, BudgetDate budgetDate);
    public void updateOrInsertPlaidAccess(String accessToken, String itemId, String requestId, User currentUser);
    public PlaidAccessTokens getPlaidAccessToken(User currentUser);
    public void updateOrInsertPlaidBankAccount(PlaidBankAccount plaidBankAccount);
    public List<PlaidBankAccount> getBanksByUserId(User currentUser);
    // calculateBudgetSummary(Int budgetId);
}
