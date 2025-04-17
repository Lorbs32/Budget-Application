package com.budget.app.service.financialGoal;

import com.budget.app.entity.FinancialGoal;

import java.util.List;

public interface FinancialGoalService {
	void saveFinancialGoal(FinancialGoal financialGoal);
	List<FinancialGoal> getFinancialGoalsByBudgetId(int budgetId);
}
