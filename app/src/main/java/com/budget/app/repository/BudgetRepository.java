package com.budget.app.repository;

import com.budget.app.entity.Budget;
import com.budget.app.entity.BudgetDate;
import com.budget.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Integer> {
	@NativeQuery(value =
			"SELECT * FROM BUDGETS B WHERE B.USER_ID = ?1 AND B.DATE_ID = ?2")
	Budget findByUserIdAndBudgetDateId(int userId, int budgetDateId);
	Budget findByUserId(int userId);
	Budget findById(int budgetId);
	Budget findByUserAndBudgetDate(User currentUser, BudgetDate budgetDate);
}