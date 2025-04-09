package com.budget.app.service;

import com.budget.app.entity.*;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

public interface LineItemService {

    public LineItem createLineItem(LineItem lineItem);
    public List<LineItem> getAllLineItems();
    public List<LineItem> getLineItemsByIncomeType(boolean isIncome);
    public boolean isActiveForMonth(LineItem item, YearMonth targetMonth);
    public void updateOrInsertLineItem(LineItem lineitem);
    public LineItem findById(int lineItemId);
}
