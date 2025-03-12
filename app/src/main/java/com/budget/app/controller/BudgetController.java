package com.budget.app.controller;

import com.budget.app.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/budgets")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    // @GetMapping("/budgetId/summary")
    // public String getBudgetSummary(@PathVariable int budgetId, Model model);
        // Map
    // return "budgetSummary";
}
