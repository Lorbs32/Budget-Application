package com.budget.app.entity;

import jakarta.persistence.*;

@Entity
public class FinancialGoal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String goalName;
	@ManyToOne
	private BudgetDate budgetDate;

	public BudgetDate getBudgetDate() {
		return budgetDate;
	}

	public void setBudgetDate(BudgetDate budgetDate) {
		this.budgetDate = budgetDate;
	}

	public String getGoalName() {
		return goalName;
	}

	public void setGoalName(String goalName) {
		this.goalName = goalName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
