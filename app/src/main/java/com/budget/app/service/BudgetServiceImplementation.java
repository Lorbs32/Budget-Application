package com.budget.app.service;

import com.budget.app.entity.BudgetDate;
import com.budget.app.entity.User;
import com.budget.app.repository.BudgetDateRepository;
import com.budget.app.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
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
        ids.add(currentBudgetDateId - 2);
        ids.add(currentBudgetDateId - 1);
        ids.add(currentBudgetDateId);
        ids.add(currentBudgetDateId + 1);
        ids.add(currentBudgetDateId + 2);
        return budgetDateRepository.findByIdIn(ids);
    }
}
