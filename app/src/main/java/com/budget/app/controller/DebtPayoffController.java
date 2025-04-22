package com.budget.app.controller;

import com.budget.app.domain.DebtPayoffInput;
import com.budget.app.domain.DebtPayoffResult;
import com.budget.app.service.DebtPayoffService;
import com.budget.app.domain.DebtInput;
import com.budget.app.domain.DebtPayoffResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/debt-payoff")
public class DebtPayoffController {

    @Autowired
    private DebtPayoffService debtPayoffService;

    @GetMapping
    public String showForm(Model model) {
        DebtPayoffInput input = new DebtPayoffInput();
        input.setDebts(List.of(new DebtInput()));
        input.setExtraPayment(0.0);
        model.addAttribute("debtPayoffInput", input);
        return "debt-payoff-form";
    }

    @PostMapping
    public String calculatePayoff(@ModelAttribute("debtPayoffInput") DebtPayoffInput input, Model model) {
        // ✅ Filter out empty debts
        List<DebtInput> cleaned = input.getDebts().stream()
                .filter(debt -> debt.getName() != null && !debt.getName().isBlank())
                .filter(debt -> debt.getBalance() > 0 || debt.getMinPayment() > 0 || debt.getInterestRate() > 0)
                .toList();

        input.setDebts(cleaned); // replace with filtered list

        DebtPayoffResult result = debtPayoffService.calculate(input);
        model.addAttribute("debtPayoffResult", result);
        return "debt-payoff-result";
    }
}
