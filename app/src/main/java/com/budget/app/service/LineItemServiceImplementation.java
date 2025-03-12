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
    public LineItem createLineItem(String lineItemName, BigDecimal plannedAmount, Boolean isIncome, int categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        LineItem newLineItem = new LineItem(lineItemName, plannedAmount, isIncome, category, new ArrayList<>());
        return lineItemRepository.save(newLineItem);
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
