package com.budget.app.service;

import com.budget.app.entity.*;
import com.budget.app.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BudgetServiceImplementation implements BudgetService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BudgetDateRepository budgetDateRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private LineItemRepository lineItemRepository;

    @Autowired
    private TransactionRepository transactionRepository;

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
    public List<Group> getGroups(int budgetId)
    {
        return groupRepository.findById(budgetId);
    }

    @Override
    public List<LineItem> getLineItems(List<Group> groups)
    {
        List<Integer> ids = new ArrayList<Integer>();
        for(Group group: groups)
        {
            ids.add(group.getId());
        }
        return lineItemRepository.findByIdIn(ids);
    }

    @Override
    public List<Transaction> getTransactions(List<LineItem> lineItems)
    {
        List<Integer> ids = new ArrayList<Integer>();
        for(LineItem lineItem: lineItems)
        {
            ids.add(lineItem.getId());
        }
        return transactionRepository.findByIdIn(ids);
    }
}
