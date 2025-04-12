package com.budget.app.controller;

import com.budget.app.domain.DebtInput;
import com.budget.app.domain.DebtPayoffResult;
import com.budget.app.entity.*;
import com.budget.app.security.model.CustomUserDetails;
import com.budget.app.service.BudgetService;
import com.budget.app.service.DebtPayoffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

//@Controller
//@RequestMapping("/dashboard")
//public class DebtPayoffController {
//
//    @Autowired
//    private BudgetService budgetService;
//
//    private final DebtPayoffService debtPayoffService;
//
//    public DebtPayoffController(DebtPayoffService debtPayoffService) {
//        this.debtPayoffService = debtPayoffService;
//    }
//
//    // Handle form submission
//    @PostMapping("/debt-payoff")
//    public String handleDebtPayoffForm(
//            @RequestParam(name = "budgetDateId") Integer budgetDateId,
//            @RequestParam(name = "name") List<String> names,
//            @RequestParam(name = "balance") List<Double> balances,
//            @RequestParam(name = "minPayment") List<Double> minPayments,
//            @RequestParam(name = "interestRate") List<Double> interestRates,
//            @RequestParam(name = "extraPayment") double extraPayment,
//            Model model
//    ) {
//        // Step 1: Recreate debt input list
//        List<DebtInput> debts = new ArrayList<>();
//        for (int i = 0; i < names.size(); i++) {
//            DebtInput debt = new DebtInput();
//            debt.setName(names.get(i));
//            debt.setBalance(balances.get(i));
//            debt.setMinPayment(minPayments.get(i));
//            debt.setInterestRate(interestRates.get(i));
//            debts.add(debt);
//        }
//
//        // Step 2: Run calculator logic
//        DebtPayoffResult result = debtPayoffService.calculatePayoff(debts, extraPayment);
//
//        // Step 3: Rebuild dashboard model
//
//        // 3a. User info
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = ((CustomUserDetails) auth.getPrincipal()).getUser();
//
//        // 3b. Get budget date
//        BudgetDate budgetDateSelected = budgetService.getBudgetDateById(budgetDateId);
//        model.addAttribute("budgetDateSelected", budgetDateSelected);
//
//        // 3c. All dates (for month switcher)
//        List<BudgetDate> budgetDates = budgetService.getBudgetDatesBetween(budgetDateSelected.getStartDate());
//        model.addAttribute("budgetDates", budgetDates);
//
//        // 3d. Get budget
//        Budget budget = budgetService.getBudget(user.getId(), budgetDateSelected.getId());
//        model.addAttribute("budget", budget);
//        model.addAttribute("budgetId", budget.getId());
//
//        // 3e. Get categories + line items
//        List<Category> categories = budgetService.getCategories(budget.getId());
//        model.addAttribute("categories", categories);
//
//        List<LineItem> lineItems = budgetService.getLineItemsByCategoryIds(categories);
//        model.addAttribute("lineItems", lineItems);
//
//        // 3f. Optional: pie chart data (can copy from MainController if needed)
//
//        // Step 4: Form results
//        model.addAttribute("debts", debts);
//        model.addAttribute("extraPayment", extraPayment);
//        model.addAttribute("result", result);
//
//        // Step 5: Mark this month as active
//        model.addAttribute("isBudgetCreatedInCurrentMonth", "YES");
//
//        System.out.println("📥 Received budgetDateId from form: " + budgetDateId);
//        return "dashboard";
//    }
//}
