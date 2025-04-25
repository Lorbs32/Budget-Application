package com.budget.app.service.financialGoal;

import com.budget.app.entity.FinancialGoal;
import com.budget.app.repository.FinancialGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinancialGoalServiceMethods implements FinancialGoalService {
	@Autowired
	private FinancialGoalRepository financialGoalRepository;

	@Override
	public void saveFinancialGoal(FinancialGoal financialGoal) {
		financialGoalRepository.save(financialGoal);
	}

	@Override
	public List<FinancialGoal> getFinancialGoalsByBudgetDateId(int budgetDateId) {
		return financialGoalRepository.getFinancialGoalsByBudgetDateId(budgetDateId);
	}

	@Override
	public void deleteFinancialGoalById(int financialGoalId) {
		financialGoalRepository.deleteById(financialGoalId);
	}
}
