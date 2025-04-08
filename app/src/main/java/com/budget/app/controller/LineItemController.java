package com.budget.app.controller;

import com.budget.app.entity.*;
import com.budget.app.repository.CategoryRepository;
import com.budget.app.service.LineItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/lineitems")
public class LineItemController {

    @Autowired
    private LineItemService lineItemService;

    @Autowired
    private CategoryRepository categoryRepository;

    // Page to Display all Line Items
    @GetMapping
    public String getAllLineItems(@RequestParam(required = false) Boolean isIncome, Model model) {
        List<LineItem> lineItems = (isIncome == null) ?
                lineItemService.getAllLineItems() :
                lineItemService.getLineItemsByIncomeType(isIncome);

        model.addAttribute("lineItems", lineItems);
        return "displayAllLineItems";  // Returns the Thymeleaf view
    }

    // Page to create a new Line Item
    @GetMapping("/create")
    public String showCreateLineItemForm(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("lineItem", new LineItem());  // Prepares an empty object for binding
        model.addAttribute("categories", categories);
        return "addLineItem";  // ✅ Updated view name
    }
    @PostMapping("/create")
    public String createLineItem(@ModelAttribute LineItem lineItem, @RequestParam("category.id") int categoryId) {
        Category category = categoryRepository.findById(categoryId);
        lineItem.setCategory(category);
        lineItemService.createLineItem(lineItem);
        return "redirect:/dashboard";
    }

    @PostMapping("/updateLineItem")
    public String updateLineItem(@RequestParam int lineItemId, @RequestParam BigDecimal plannedAmount)
    {
        LineItem lineItem = lineItemService.findById(lineItemId);
        lineItem.setId(lineItemId);
        lineItem.setPlannedAmount(plannedAmount);

        lineItemService.updateOrInsertLineItem(lineItem);
        return "redirect:/dashboard";
    }
}
