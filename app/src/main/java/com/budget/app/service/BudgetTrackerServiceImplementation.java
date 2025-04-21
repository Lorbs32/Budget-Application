package com.budget.app.service;
import com.budget.app.entity.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Transactional
public class BudgetTrackerServiceImplementation implements BudgetTrackerService {

    @Autowired
    private BudgetService budgetService;

    private BigDecimal calculateBalance() {
        BigDecimal balance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        return balance;
    }

    @Override
    public List<BudgetTracker> getBudgetTrackerForBudgetDates(List<BudgetDate> budgetDates) {
        List<BudgetTracker> budgetTrackers = new ArrayList<>();

        for (int i=0; i < budgetDates.size(); i++) {
            int budgetDateId = budgetDates.get(i).getId();
            BigDecimal balance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
            BudgetTracker budgetTracker = new BudgetTracker();
            BudgetDate budgetDateSelected = budgetService.getBudgetDateById(budgetDateId);
            budgetTracker.setBudgetDate(budgetDateSelected);
            balance = calculateBalance();
            budgetTracker.setBalance(balance);
            budgetTrackers.add(budgetTracker);
            //budgetTrackers.add(budgetDateRepository.findByBudgetDateBetween(date));
        }
        return budgetTrackers;
    }


    @Override
    public List<BudgetTracker> getBudgetTrackerForYear(int year) {
        List<BudgetTracker> budgetTrackers = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            LocalDate date = LocalDate.of(year, month, 1);
            //budgetTrackers.add(budgetDateRepository.findByBudgetDateBetween(date));
        }
        return budgetTrackers;
    }

}
