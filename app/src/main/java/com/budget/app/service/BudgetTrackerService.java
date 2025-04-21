package com.budget.app.service;

import com.budget.app.entity.BudgetDate;
import com.budget.app.entity.BudgetTracker;
import java.util.List;

public interface BudgetTrackerService {
    public List<BudgetTracker> getBudgetTrackerForBudgetDates(List<BudgetDate> budgetDates);
    public List<BudgetTracker> getBudgetTrackerForYear(int year);

    private String expandMonthAbbreviation(String shortMonth) {
        switch (shortMonth.toUpperCase()) {
            case "JAN": return "JANUARY";
            case "FEB": return "FEBRUARY";
            case "MAR": return "MARCH";
            case "APR": return "APRIL";
            case "MAY": return "MAY";
            case "JUN": return "JUNE";
            case "JUL": return "JULY";
            case "AUG": return "AUGUST";
            case "SEP": return "SEPTEMBER";
            case "OCT": return "OCTOBER";
            case "NOV": return "NOVEMBER";
            case "DEC": return "DECEMBER";
            default: throw new IllegalArgumentException("Invalid month abbreviation: " + shortMonth);
        }
    }
}
