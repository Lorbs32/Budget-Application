package com.budget.app.controller;

import com.budget.app.domain.ExtractParameter;
import com.budget.app.entity.*;
import com.budget.app.security.model.CustomUserDetails;
import com.budget.app.service.BudgetDateService;
import com.budget.app.service.BudgetService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

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

        BudgetDate budgetDate = null;

        // Validate inputs
        if (start == null)
        {
            model.addAttribute("error", "Start date is required.");
        }
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

    @PostMapping("/addBudget")
    public String addBudget(Model model,
                            HttpServletRequest request)
    {
        // All requests that redirect to the dashboard need to retrieve the currently selected budget date ID and pass it through.
        // In this case also use the budget date ID to create the new budget, and minus one to check for budget from last month.
        String referrer = request.getHeader("referer");
        String budgetDateId = ExtractParameter.getParameterValue(referrer, "budgetDateId");

        // Get authenticated user.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();

        // budget ID from URL params minus 1 budget date ID last month.
        int lastMonthBudgetDateId = parseInt(budgetDateId) - 1;
        Budget lastMonthBudget = budgetService.getBudget(currentUser.getId(),lastMonthBudgetDateId);

        // Check whether there's a budget last month.
        if(lastMonthBudget != null)
        {
            // Pull last month's categories, and line items. (Basically subscriptions)?
            BudgetDate budgetDate = budgetService.getBudgetDateById(lastMonthBudgetDateId + 1);
            List<Category> categories = budgetService.getCategories(lastMonthBudget.getId());
            List<LineItem> lineItems = budgetService.getLineItemsByCategoryIds(categories);
            // Use those categories and line items to generate a new month's budget.
            budgetService.addBudgetBasedOnLastMonth(currentUser, lastMonthBudget, categories, lineItems, budgetDate);
        }
        else
        {
            // Otherwise, create a budget based on predefined data.
            BudgetDate budgetDate = budgetService.getBudgetDateById(parseInt(budgetDateId));
            budgetService.addBudgetPredetermined(currentUser, budgetDate);
        }

        return "redirect:../dashboard?budgetDateId=" + budgetDateId;
    }
}