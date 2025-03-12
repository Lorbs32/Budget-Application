package com.budget.app.controller;

import com.budget.app.entity.*;
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
        model.addAttribute("lineItem", new LineItem());  // Prepares an empty object for binding
        return "addLineItem";  // ✅ Updated view name
    }
    @PostMapping("/create")
    public String createLineItem(@ModelAttribute LineItem lineItem) {
        lineItemService.createLineItem(lineItem);
        return "redirect:/dashboard";
    }
}
