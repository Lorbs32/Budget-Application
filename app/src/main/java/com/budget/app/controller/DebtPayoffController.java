package com.budget.app.controller;

import com.budget.app.domain.DebtInput;
import com.budget.app.domain.DebtPayoffResult;
import com.budget.app.service.DebtPayoffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DebtPayoffController {

    private final DebtPayoffService debtPayoffService;

    public DebtPayoffController(DebtPayoffService debtPayoffService) {
        this.debtPayoffService = debtPayoffService;
    }

    // Show form on the dashboard page
    @GetMapping
    public String showDashboard(Model model) {
        List<DebtInput> debts = new ArrayList<>();
        debts.add(new DebtInput());

        model.addAttribute("debts", debts);
        model.addAttribute("extraPayment", 0.0);
        return "dashboard";
    }

    // Handle form submission
    @PostMapping("/debt-payoff")
    public String handleDebtPayoffForm(
            @RequestParam(name = "name") List<String> names,
            @RequestParam(name = "balance") List<Double> balances,
            @RequestParam(name = "minPayment") List<Double> minPayments,
            @RequestParam(name = "interestRate") List<Double> interestRates,
            @RequestParam(name = "extraPayment") double extraPayment,
            Model model
    ) {
        List<DebtInput> debts = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            DebtInput debt = new DebtInput();
            debt.setName(names.get(i));
            debt.setBalance(balances.get(i));
            debt.setMinPayment(minPayments.get(i));
            debt.setInterestRate(interestRates.get(i));
            debts.add(debt);
        }

        DebtPayoffResult result = debtPayoffService.calculatePayoff(debts, extraPayment);

        // Re-display form with inputs pre-filled
        model.addAttribute("debts", debts);
        model.addAttribute("extraPayment", extraPayment);
        model.addAttribute("result", result);

        return "dashboard";
    }
}
