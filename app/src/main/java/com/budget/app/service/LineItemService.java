package com.budget.app.service;

import com.budget.app.entity.*;
import java.math.BigDecimal;
import java.util.List;

public interface LineItemService {

    public LineItem createLineItem(LineItem lineItem);
    public List<LineItem> getAllLineItems();
    public List<LineItem> getLineItemsByIncomeType(boolean isIncome);
}
