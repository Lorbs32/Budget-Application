package com.budget.app.repository;

import com.budget.app.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Category, Integer> {
	@NativeQuery(value = "SELECT * FROM CATEGORIES G WHERE G.BUDGET_ID = ?1")
	List<Category> findById(int budgetId);
}