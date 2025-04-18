package com.budget.app.service;

import com.budget.app.entity.*;
import com.budget.app.repository.CategoryRepository;
import com.budget.app.repository.LineItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
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
        Category category = categoryRepository.findById(lineItem.getCategory().getId());
        lineItem.setCategory(category);
        if (lineItem.getRecurrenceType() == null) {
            lineItem.setRecurrenceType(RecurrenceType.ONE_TIME); // Set default value to ONE_TIME
        }
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

    // Decide whether subscriptions should show for future months
    @Override
    public boolean isActiveForMonth(LineItem item, YearMonth targetMonth) {
        RecurrenceType recurrence = item.getRecurrenceType();

        if (recurrence == RecurrenceType.ONE_TIME) {
            if (item.getTransactions().isEmpty())
                return false;
            for (Transaction tx : item.getTransactions()) {
                YearMonth txMonth = YearMonth.from(tx.getTransactionDate());
                if (txMonth.equals(targetMonth)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    @Transactional
    public void updateOrInsertLineItem(LineItem lineItem)
    {
        lineItemRepository.updateOrInsert(lineItem);
    }

    @Override
    public LineItem findById(int lineItemId){
        return lineItemRepository.findById(lineItemId);
    }

    public void delete(LineItem deleteLineItem) {
        lineItemRepository.delete(deleteLineItem);
    }
}
