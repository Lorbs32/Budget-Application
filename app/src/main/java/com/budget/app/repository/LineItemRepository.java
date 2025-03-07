package com.budget.app.repository;

import com.budget.app.entity.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineItemRepository extends JpaRepository<LineItem, Integer> {
	List<LineItem> findByIdIn(List<Integer> ids);
}
