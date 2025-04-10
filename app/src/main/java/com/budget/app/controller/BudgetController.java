package com.budget.app.controller;

import com.budget.app.entity.BudgetDate;
import com.budget.app.service.BudgetDateService;
import com.budget.app.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/budgets")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private BudgetDateService budgetDateService;

    // @GetMapping("/budgetId/summary")
    // public String getBudgetSummary(@PathVariable int budgetId, Model model);
    // Map
    // return "budgetSummary";
    // Show the date selection form
    @GetMapping("/selectDateRange")
    public String showDateSelectionForm(Model model) {
        return "budgetForm";

    }

    @GetMapping("/getBudget")
    public String getBudget(@RequestParam(required = false) String startDate,
                            @RequestParam(required = false) String endDate,
                            Model model) {

        LocalDate start = startDate != null ? LocalDate.parse(startDate) : null;
        LocalDate end = endDate != null ? LocalDate.parse(endDate) : null;
        System.out.println(start);
        System.out.println(end);


        //List<BudgetDate> budgets = new ArrayList<>();
        BudgetDate budgetDate = null;

        // Validate inputs
        if (start == null)
        {
            model.addAttribute("error", "Start date is required.");
        }
//        else if (start.isAfter(end))
//        {
//            model.addAttribute("error", "Start Date must be before End Date.");
//        }
        else {
            budgetDate = budgetDateService.budgetForDateRange(start);

            if (budgetDate == null) {
                model.addAttribute("error", "No budgets found for the selected date range.");
            }
            else
            {
                model.addAttribute("error","");
            }
        }

        // Add the budgets to the model
//        model.addAttribute("budget", budget);
//        model.addAttribute("startDate", startDate);
//        model.addAttribute("endDate", endDate);

        return "redirect:../dashboard?budgetDateId=" + budgetDate.getId();
    }
}

//${error}
//${budgets}
//${startDate}
//${endDate}
//${budget.month}
//${budget.amount}