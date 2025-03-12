package com.budget.app.service;

import com.budget.app.entity.*;
import com.budget.app.repository.CategoryRepository;
import com.budget.app.repository.LineItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LineItemServiceImplementation implements LineItemService {

    @Autowired
    private LineItemRepository lineItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    // Creates a LineItem
    public LineItem createLineItem(LineItem lineItem) {
        // Fetch the Category object based on the category ID from the submitted form
        Category category = categoryRepository.findById(lineItem.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Assign the fetched category to the LineItem
        lineItem.setCategory(category);

        // Save the LineItem to the database
        return lineItemRepository.save(lineItem);
    }

    // Retrieves all LineItems
    @Override
    public List<LineItem> getAllLineItems() {
        return lineItemRepository.findAll();
    }

    // Retrieve LineItems by Income/Expenses
    @Override
    public List<LineItem> getLineItemsByIncomeType(boolean isIncome) {
        return lineItemRepository.findByIsIncome(isIncome);
    }
}
