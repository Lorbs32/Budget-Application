package com.budget.app.service;

import com.budget.app.entity.*;
import com.budget.app.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.budget.app.entity.RecurrenceType;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> findByBudgetId(int budgetId) {
        return transactionRepository.findByBudgetId(budgetId);
    }

    public void createRecurringTransactions(User user, Budget lastMonthBudget, BudgetDate newBudgetDate,
                                            Map<LineItem, LineItem> oldToNewLineItemMap) {
        List<Transaction> lastMonthTransactions = transactionRepository.findByBudgetId(lastMonthBudget.getId());

        for (Transaction oldTransaction : lastMonthTransactions) {
            LineItem lineItem = oldTransaction.getLineItem();

            if (lineItem.getRecurrenceType() == RecurrenceType.MONTHLY) {
                Transaction newTransaction = new Transaction();
                LineItem newLineItem = oldToNewLineItemMap.get(lineItem);
                newTransaction.setLineItem(newLineItem);
                newTransaction.setActualAmount(oldTransaction.getActualAmount());
                newTransaction.setMerchant(oldTransaction.getMerchant());
                newTransaction.setNote("Recurring from previous month");

                // Add 1 month to transaction date
                LocalDate originalDate = oldTransaction.getTransactionDate();
                LocalDate proposedDate = originalDate.plusMonths(1);

                // Cap to the 28th for transactions on the 31st for example
                int safeDay = Math.min(originalDate.getDayOfMonth(), 28);
                LocalDate safeFutureDate = LocalDate.of(
                        proposedDate.getYear(),
                        proposedDate.getMonth(),
                        safeDay
                );

                newTransaction.setTransactionDate(safeFutureDate);

                transactionRepository.save(newTransaction);

                // output check
                System.out.println("Created recurring transaction:");
                System.out.println("LineItem: " + lineItem.getLineItemName());
                System.out.println("Amount: $" + newTransaction.getActualAmount());
                System.out.println("Date: " + newTransaction.getTransactionDate());
            }
        }
    }
}
