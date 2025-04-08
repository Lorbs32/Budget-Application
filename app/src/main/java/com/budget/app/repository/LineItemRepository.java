package com.budget.app.repository;

import com.budget.app.entity.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineItemRepository extends JpaRepository<LineItem, Integer> {
	List<LineItem> findByCategoryIdIn(List<Integer> ids);
	List<LineItem> findByIsIncome(boolean isIncome);
	LineItem findById(int lineItemId);
	default void updateOrInsert(LineItem lineItem) { save(lineItem);}
}