package com.budget.app.service;

import com.budget.app.entity.*;
import java.math.BigDecimal;
import java.util.List;

public interface LineItemService {

    public LineItem createLineItem(String lineItemName, BigDecimal plannedAmount, Boolean isIncome, int categoryId);
    public List<LineItem> getAllLineItems();
    public List<LineItem> getLineItemsByIncomeType(boolean isIncome);
}
