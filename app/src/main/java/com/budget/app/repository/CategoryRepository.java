package com.budget.app.repository;

import com.budget.app.entity.Category;
import com.budget.app.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	@NativeQuery(value =
			"SELECT * FROM CATEGORIES C WHERE C.BUDGET_ID = ?1")
	List<Category> findByBudgetId(int budgetId);

	Category findById(int categoryId);
	default Category updateOrInsert(Category category){return save(category);}

}