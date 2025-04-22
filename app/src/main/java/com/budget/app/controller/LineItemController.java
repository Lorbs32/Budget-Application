package com.budget.app.controller;

import com.budget.app.domain.ExtractParameter;
import com.budget.app.entity.*;
import com.budget.app.repository.CategoryRepository;
import com.budget.app.service.LineItemService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import java.util.List;

@Controller
@RequestMapping("/lineitems")
public class LineItemController {

    @Autowired
    private LineItemService lineItemService;

    @Autowired
    private CategoryRepository categoryRepository;

    // Page to Display all Line Items
//    @GetMapping
//    public String getAllLineItems(@RequestParam(required = false) Boolean isIncome, Model model) {
//        List<LineItem> lineItems = (isIncome == null) ?
//                lineItemService.getAllLineItems() :
//                lineItemService.getLineItemsByIncomeType(isIncome);
//
//        model.addAttribute("lineItems", lineItems);
//        return "displayAllLineItems";  // Returns the Thymeleaf view
//    }

    // Page to create a new Line Item
//    @GetMapping("/create")
//    public String showCreateLineItemForm(Model model) {
//        List<Category> categories = categoryRepository.findAll();
//        model.addAttribute("lineItem", new LineItem());  // Prepares an empty object for binding
//        model.addAttribute("categories", categories);
//        return "addLineItem";  // ✅ Updated view name
//    }
    @PostMapping("/create")
    public String createLineItem(@ModelAttribute LineItem lineItem,
                                 @RequestParam("category.id") int categoryId,
                                 HttpServletRequest request)
    {
        Category category = categoryRepository.findById(categoryId);
        lineItem.setCategory(category);
        lineItemService.createLineItem(lineItem);

        // All requests that redirect to the dashboard need to retrieve the currently selected budget date ID and pass it through.
        String referrer = request.getHeader("referer");
        String budgetDateId = ExtractParameter.getParameterValue(referrer, "budgetDateId");

        return "redirect:../dashboard?budgetDateId=" + budgetDateId;
        //return "redirect:/dashboard";
    }

    @PostMapping("/updateLineItem")
    public String updateLineItem(@RequestParam int lineItemId,
                                 @RequestParam String lineItemName,
                                 @RequestParam BigDecimal plannedAmount,
                                 @RequestParam boolean isIncome,
                                 @RequestParam String recurrenceType,
                                 @RequestParam("category.id") int categoryId,
                                 HttpServletRequest request) {

        LineItem lineItem = lineItemService.findById(lineItemId);

        lineItem.setLineItemName(lineItemName);
        lineItem.setPlannedAmount(plannedAmount);
        lineItem.setIncome(isIncome);
        lineItem.setRecurrenceType(RecurrenceType.valueOf(recurrenceType));
        lineItem.setCategory(categoryRepository.findById(categoryId));

        lineItemService.updateOrInsertLineItem(lineItem);

        // Maintain current budget month view
        String referrer = request.getHeader("referer");
        String budgetDateId = ExtractParameter.getParameterValue(referrer, "budgetDateId");

        return "redirect:../dashboard?budgetDateId=" + budgetDateId;
    }

    @PostMapping("/delete")
    public String deleteLineItem(@RequestParam ("lineItem.id") int lineItemId,
                                 HttpServletRequest request)
    {
        LineItem deleteLineItem = lineItemService.findById(lineItemId);
        lineItemService.delete(deleteLineItem);

        String referrer = request.getHeader("referer");
        String budgetDateId = ExtractParameter.getParameterValue(referrer, "budgetDateId");
        return "redirect:../dashboard?budgetDateId=" + budgetDateId;
    }
}
