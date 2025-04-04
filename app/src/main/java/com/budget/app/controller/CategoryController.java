package com.budget.app.controller;

import com.budget.app.entity.*;
import com.budget.app.repository.CategoryRepository;
import com.budget.app.service.BudgetService;
import com.budget.app.service.CategoryService;
import com.budget.app.service.LineItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String addCategory(@RequestParam(value = "categoryName", defaultValue = "DefaultCategory") String categoryName,
                              @RequestParam("budget") int budgetId, Model model) {
        Budget budget = budgetService.getBudgetById(budgetId);

        Category category = new Category();
        category.setCategoryName(categoryName);
        category.setBudget(budget);

        categoryService.saveCategory(category);
        model.addAttribute("message", "Category added successfully!");
        return "redirect:/dashboard";
    }

    @GetMapping("/retrieveForm")
    @ResponseBody
    public String getAddCategoryForm()
    {
        return "<form th:action=\"@{/addCategory/createCategory}\" method=\"post\" class=\"form-group form-inline\">" +
            "<input type=\"hidden\" th:name=\"budget\" th:value=\"${budget.id}\"/>" +
            "<label for=\"categoryName\" class=\"form-label\">Category Name:</label>" +
            "<input type=\"text\" id=\"categoryName\" name=\"categoryName\" class=\"form-control form-control-override ms-3 me-3\" required>" +
            "<button type=\"submit\" class=\"btn btn-primary rounded-pill\">Add Category</button></form>";

//                "<form>" +
//                "<label for\"categoryName\">Category Name</label>" +
//                "<input type=\"text\" class=\"form-control\" name=\"categoryName\" id=\"categoryName\">" +
//                "<input type=\"submit\" value=\"Add Note\" class=\"btn btn-primary mt-2\">"
//                "</form>";
    }

}