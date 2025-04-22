package com.budget.app.controller;

import com.budget.app.domain.ExtractParameter;
import com.budget.app.entity.*;
import com.budget.app.service.BudgetService;
import com.budget.app.service.CategoryService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
@RequestMapping("/addCategory")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BudgetService budgetService;

//    @GetMapping("/createCategory")
//    public String showCreateCategoryForm(Model model)
//    {
//        List<Budget> budgets= budgetService.getAllBudgets();
//        model.addAttribute("category", new Category());
//        model.addAttribute("budgets", budgets);
//        return "dashboard";
//    }

    @PostMapping("/createCategory")
    public String addCategory(@RequestParam(value = "categoryName", defaultValue = "DefaultCategory", required = false) String categoryName,
                              @RequestParam(defaultValue = "1") int budgetId, Model model,
                              HttpServletRequest request) {

        //Budget budget = budgetService.getBudgetById(budgetId);
        //BudgetDate budgetDate = budgetService.getBudgetDateById(budget.getBudgetDate().getId());
        //Budget getBudgetByBudgetDateAndUser(User currentUser, BudgetDate budgetDate);
        Budget budget = budgetService.getBudgetByBudgetId(budgetId);

        Category category = new Category();
        category.setCategoryName(categoryName);
        category.setBudget(budget);

        categoryService.saveCategory(category);
        model.addAttribute("message", "Category added successfully!");

        // All requests that redirect to the dashboard need to retrieve the currently selected budget date ID and pass it through.
        String referrer = request.getHeader("referer");
        String budgetDateId = ExtractParameter.getParameterValue(referrer, "budgetDateId");

        return "redirect:../dashboard?budgetDateId=" + budgetDateId;
        //return "redirect:../dashboard";
    }

    @GetMapping("/retrieveForm")
    @ResponseBody
    public String getAddCategoryForm(@RequestParam("budgetId") int budgetId)
    {
        return "<form class=\"form-group form-inline\"" +
                "hx-post=\"/addCategory/createCategory\" hx-headers='js:{\"X-CSRF-TOKEN\": calculateValue()}' hx-refresh=\"true\"" +
                "hx-target=\"#fullPage\" hx-swap=\"outerHTML\" hx-vals='js:{\"budgetId\": getBudgetId()}'>" +
                "<input type=\"hidden\" name=\"budget\" value=\"" + budgetId + "\"/>" +
                "<label for=\"categoryName\" class=\"form-label\">Category Name:</label>" +
                "<input type=\"text\" id=\"categoryName\" name=\"categoryName\" class=\"form-control form-control-override ms-3 me-3\" required>" +
                "<button type=\"submit\" class=\"btn btn-secondary rounded-pill\">Add Category</button></form>";
    }

}