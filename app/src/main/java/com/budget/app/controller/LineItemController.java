package com.budget.app.controller;

import com.budget.app.entity.*;
import com.budget.app.repository.CategoryRepository;
import com.budget.app.service.LineItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/lineitems")
public class LineItemController {

    @Autowired
    private LineItemService lineItemService;
    @Autowired
    private CategoryRepository categoryRepository;

    // Display all Line Items
    @GetMapping
    public String getAllLineItems(@RequestParam(required = false) Boolean isIncome, Model model) {
        List<LineItem> lineItems = (isIncome == null) ?
                lineItemService.getAllLineItems() :
                lineItemService.getLineItemsByIncomeType(isIncome);

        model.addAttribute("lineItems", lineItems);
        return "displayAllLineItems";  // Returns the Thymeleaf view
    }


    // Create a new Line Items
    @GetMapping("/create")
    public String showCreateLineItemForm(Model model) {
        model.addAttribute("lineItem", new LineItem());  // Prepares an empty object for binding
        model.addAttribute("categories", categoryRepository.findAll()); // adds list of categories
        model.addAttribute("recurrenceTypes", RecurrenceType.values()); // adds recurrence type <select>
        return "addLineItem";  // ✅ Updated view name
    }
    @PostMapping("/create")
    public String createLineItem(@ModelAttribute LineItem lineItem) {
        lineItemService.createLineItem(lineItem);
        return "redirect:/dashboard";
    }
}
