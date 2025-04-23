package com.budget.app.repository;

import com.budget.app.entity.FinancialGoal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinancialGoalRepository extends JpaRepository<FinancialGoal, Integer> {
	List<FinancialGoal> getFinancialGoalsByBudgetId(int budgetId);
}
