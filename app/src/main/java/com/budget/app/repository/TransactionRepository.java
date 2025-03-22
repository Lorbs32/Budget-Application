package com.budget.app.repository;

import com.budget.app.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	List<Transaction> findByIdIn(List<Integer> ids);
    default Transaction updateOrInsert(Transaction transaction){return save(transaction);}
    List<Transaction> findByLineItemId(int id);
}