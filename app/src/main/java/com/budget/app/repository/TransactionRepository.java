package com.budget.app.repository;

import com.budget.app.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	List<Transaction> findByIdIn(List<Integer> ids);
    default Transaction updateOrInsert(Transaction transaction){return save(transaction);}
    List<Transaction> findByLineItemId(int id);
    List<Transaction> findByLineItemIdIn(List<Integer> ids);

    // Fetching budgetID for subscription transactions
    @Query("SELECT t FROM Transaction t WHERE t.lineItem.category.budget.id = :budgetId ORDER BY t.transactionDate ASC")
    List<Transaction> findByBudgetId(@Param("budgetId") int budgetId);
}