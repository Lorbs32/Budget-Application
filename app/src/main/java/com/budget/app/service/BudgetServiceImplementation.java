package com.budget.app.service;

import com.budget.app.entity.*;
import com.budget.app.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class BudgetServiceImplementation implements BudgetService
{
    // Autowires
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BudgetDateRepository budgetDateRepository;
    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private LineItemRepository lineItemRepository;
    @Autowired
    private TransactionRepository transactionRepository;


    // Interface Method Implementations
    @Override
    public List<User> getUsers()
    {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email)
    {
        return userRepository.findByEmailAddress(email);
    }

//    @Override
//    public List<BudgetDate> getBudgetDatesBetween(LocalDate currentDate)
//    {
//        BudgetDate budgetDate = budgetDateRepository.findByBudgetDateBetween(currentDate);
//        Integer currentBudgetDateId = budgetDate.getId();
//        List<Integer> ids = new ArrayList<Integer>();
//
//        // TO DO need to write a more flexible algorithm here.
//        // This assumes 5 months to display and that the 3 month "march" is the current and selected month.
//        ids.add(currentBudgetDateId - 2);
//        ids.add(currentBudgetDateId - 1);
//        ids.add(currentBudgetDateId);
//        ids.add(currentBudgetDateId + 1);
//        ids.add(currentBudgetDateId + 2);
//        List<BudgetDate> listBudgetDates = budgetDateRepository.findByIdIn(ids);
//        listBudgetDates.get(2).setCurrentBudgetMonth(true);
//        listBudgetDates.get(2).setBudgetSelected(true);
//        return listBudgetDates;
//    }

    @Override
    public List<BudgetDate> getBudgetDatesBetween(LocalDate currentDate)
    {
        BudgetDate budgetDate = budgetDateRepository.findByBudgetDateBetween(currentDate);
        Integer currentBudgetDateId = budgetDate.getId();
        List<Integer> ids = new ArrayList<Integer>();

        // TO DO need to write a more flexible algorithm here.
        // This assumes 5 months to display and that the 3 month "march" is the current and selected month.
        ids.add(currentBudgetDateId - 2);
        ids.add(currentBudgetDateId - 1);
        ids.add(currentBudgetDateId);
        ids.add(currentBudgetDateId + 1);
        ids.add(currentBudgetDateId + 2);
        List<BudgetDate> listBudgetDates = budgetDateRepository.findByIdIn(ids);
        listBudgetDates.get(2).setCurrentBudgetMonth(true);
        listBudgetDates.get(2).setBudgetSelected(true);
        return listBudgetDates;
    }

    @Override
    public Budget getBudget(int userId, int dateId)
    {
        return budgetRepository.findByUserIdAndBudgetDateId(userId, dateId);
    }

    @Override
    public List<Category> getCategories(int budgetId)
    {
        return categoryRepository.findByBudgetId(budgetId);
    }

    @Override
    public List<LineItem> getLineItemsByCategoryIds(List<Category> categories)
    {
        List<Integer> ids = new ArrayList<Integer>();
        for(Category category : categories)
        {
            ids.add(category.getId());
        }
        return lineItemRepository.findByCategoryIdIn(ids);
    }

    @Override
    public List<Transaction> getTransactions(List<LineItem> lineItems)
    {
        List<Integer> ids = new ArrayList<Integer>();
        for(LineItem lineItem: lineItems)
        {
            ids.add(lineItem.getId());
        }
        return transactionRepository.findByLineItemIdIn(ids);

        // Change this definition on 4/9/25 for the transaction filtering task.
        // return transactionRepository.findByIdIn(ids);
    }

	// ------------------TO DO-------------------
    @Override
    public void updateOrInsert(Transaction transaction)
    {
        Transaction saveResult = transactionRepository.updateOrInsert(transaction);
    }

    @Override
    public List<Transaction> getTransactionsByLineItemId(LineItem item) {
        return transactionRepository.findByLineItemId(item.getId());
    }

    @Override
    public Budget getBudgetById(int budgetId) {
        return budgetRepository.findByUserId(budgetId);
    }

    @Override
    public Budget getBudgetByBudgetId(int budgetId) { return budgetRepository.findById(budgetId); }

    @Override
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    @Override
    public BudgetDate getBudgetDateById(int budgetDateId) {
        return budgetDateRepository.findById(budgetDateId);
    }

    @Override
    public Budget getBudgetByBudgetDateAndUser(User currentUser, BudgetDate budgetDate)
    {
        return budgetRepository.findByUserAndBudgetDate(currentUser,budgetDate);
    }

    @Override
    public void addBudgetBasedOnLastMonth(User currentUser, Budget lastMonthBudget, List<Category> categories, List<LineItem> lineItems, BudgetDate newBudgetDate)
    {
        Budget newBudget = new Budget();

        // Maybe refactor this with the overloaded constructor with currentUser and newBudgetDate.
        newBudget.setUser(currentUser);
        newBudget.setBudgetDate(newBudgetDate);
        budgetRepository.save(newBudget);
        Budget createdBudget = budgetRepository.findByUserIdAndBudgetDateId(currentUser.getId(),newBudgetDate.getId());

        int categorySize = categories.size();
        List<Category> newCategories = new ArrayList<Category>();
        for(int i = 0; i < categorySize; i++)
        {
            Category newCategory = new Category();
            newCategory.setCategoryName(categories.get(i).getCategoryName());
            newCategory.setBudget(newBudget);
            newCategories.add(newCategory);
        }
        categoryRepository.saveAll(newCategories);

        int lineItemSize = lineItems.size();
        List<LineItem> newLineItems = new ArrayList<LineItem>();
        for(int i = 0; i < lineItemSize; i++) {
            LineItem newLineItem = new LineItem();
            newLineItem.setLineItemName(lineItems.get(i).getLineItemName());
            newLineItem.setIncome(lineItems.get(i).isIncome());
            newLineItem.setRecurrenceType(lineItems.get(i).getRecurrenceType());
            newLineItem.setPlannedAmount(lineItems.get(i).getPlannedAmount());
            String previousMonthCategoryName = lineItems.get(i).getCategory().getCategoryName();
            for (Category category : newCategories) {
                // This match assumes there is only ONE category with the same name - this is unpredictable if there are multiple categories with the same name.
                if (previousMonthCategoryName.equals(category.getCategoryName())) {
                    newLineItem.setCategory(category);
                }
            }
            newLineItems.add(newLineItem);
        }
        lineItemRepository.saveAll(newLineItems);
    }

    @Override
    public void addBudgetPredetermined(User currentUser, BudgetDate budgetDate)
    {
        // Define a predetermined budget with short list of categories, and line items.
        Budget newBudget = new Budget(currentUser,budgetDate);
        budgetRepository.save(newBudget);
        Budget createdBudget = budgetRepository.findByUserIdAndBudgetDateId(currentUser.getId(),budgetDate.getId());

        // Categories: Income, Housing, Food, Entertainment
        Category income = new Category("Income",createdBudget);
        Category housing = new Category("Housing",createdBudget);
        Category food = new Category("Food",createdBudget);
        Category entertainment = new Category("Entertainment",createdBudget);
        List<Category> newCategories = new ArrayList<Category>(Arrays.asList(income,housing,food,entertainment));
//        newCategories.add(income);
//        newCategories.add(housing);
//        newCategories.add(food);
//        newCategories.add(entertainment);
        categoryRepository.saveAll(newCategories);

        // Line Items: Income (Paycheck), Housing (Rent/Mortgage, Insurance), Food (Groceries, Restaurants), Entertainment (Travel, Subscriptions)
        LineItem paycheck = new LineItem("Paycheck",BigDecimal.valueOf(2000.00),true,income,RecurrenceType.MONTHLY);
        LineItem rent = new LineItem("Rent",BigDecimal.valueOf(1200.00),false,housing,RecurrenceType.MONTHLY);
        LineItem insurance = new LineItem("Insurance",BigDecimal.valueOf(200.00),false,housing,RecurrenceType.MONTHLY);
        LineItem groceries = new LineItem("Groceries",BigDecimal.valueOf(250.00),false,food,RecurrenceType.MONTHLY);
        LineItem restaurants = new LineItem("Restaurants",BigDecimal.valueOf(100.00),false,food,RecurrenceType.MONTHLY);
        LineItem travel = new LineItem("Travel",BigDecimal.valueOf(150.00),false,entertainment,RecurrenceType.MONTHLY);
        LineItem subscriptions = new LineItem("Subscriptions",BigDecimal.valueOf(100.00),false,entertainment,RecurrenceType.MONTHLY);
        List<LineItem> newLineItems = new ArrayList<LineItem>(Arrays.asList(paycheck,rent,insurance,groceries,restaurants,travel,subscriptions));
        lineItemRepository.saveAll(newLineItems);
    }

    // ------------------TO DO-------------------

	// calculateBudgetSummary(Int budgetId)
	// Use repo to fetch all line items for a budget
	// Separate income from expenses using isIncome true or false
	// Calculate totals for income and expenses
	// Calculate remaining balance
}
