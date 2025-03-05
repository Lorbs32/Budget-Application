package com.budget.app.service;

import com.budget.app.entity.BudgetDate;
import com.budget.app.entity.User;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface BudgetService
{
    public List<User> getUsers();
    public User getUserByEmail(String email);
   // public List<BudgetDate> getBudgetDates();
   // public List<BudgetDate> getBudgetDatesRange();
    public List<BudgetDate> getBudgetDatesBetween(LocalDate currentDate);
    //public List<BudgetDate> getBudgetDateRangeById(int id);
}
