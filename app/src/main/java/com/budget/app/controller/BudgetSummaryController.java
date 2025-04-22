package com.budget.app.controller;

import com.budget.app.domain.BudgetSummaryInput;
import com.budget.app.domain.BudgetSummaryResult;
import com.budget.app.service.BudgetSummaryCalculator;
import com.budget.app.service.BudgetSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;


@Controller
@RequestMapping("/budget-summary")
public class BudgetSummaryController {

    @Autowired
    private BudgetSummaryService budgetSummaryService;

    @GetMapping
    public String showForm(Model model) {
        BudgetSummaryInput input = new BudgetSummaryInput();

        input.setIncomes(Arrays.asList(0.0, 0.0));

        Map<String, Double> expenses = new LinkedHashMap<>();
        List<String> categories = Arrays.asList("Giving", "Housing", "Food", "Transportation", "Savings");

        for (String cat : categories) {
            expenses.put(cat, 0.0);
        }

        input.setExpenses(expenses);

        model.addAttribute("budgetSummaryInput", input);
        model.addAttribute("categories", categories); // ✅ new
        return "budget-summary-form";
    }

    @PostMapping
    public String handleSummary(@ModelAttribute("budgetSummaryInput") BudgetSummaryInput input,
                                BindingResult bindingResult,
                                Model model) {

        if (bindingResult.hasErrors()) {
            System.out.println("🚨 Binding Errors:");
            bindingResult.getAllErrors().forEach(e -> System.out.println(" - " + e.getDefaultMessage()));
            model.addAttribute("budgetSummaryInput", input);
            return "budget-summary-form"; // stay on the form page if there are errors
        }

        BudgetSummaryResult result = budgetSummaryService.calculateSummary(input);
        model.addAttribute("budgetSummaryResult", result);
        return "budget-summary-result";
    }
}
