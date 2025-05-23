package com.budget.app.entity;

import java.math.BigDecimal;


import jakarta.persistence.*;


public class BudgetTracker {
	//id, date, balance
	// total income items - total expense items will be used to calculate balance
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    private BudgetDate budgetDate;
    private BigDecimal balance;
    private String budgetText;
    private String budgetTextClass;

    // Constructors
    public BudgetTracker() {}
    public BudgetTracker(int id, BudgetDate budgetDate, BigDecimal balance, String budgetText, String budgetTextClass)
    {
        this.id = id;
        this.budgetDate = budgetDate;
        this.balance = balance;
        this.budgetText = budgetText;
        this.budgetTextClass = budgetTextClass;
    }
    
	public int getId() {
		return id;
	}
    
	public void setId(int id) {
		this.id = id;
	}

	public BudgetDate getBudgetDate() {
		return budgetDate;
	}
    
	public void setBudgetDate(BudgetDate budgetDate) {
		this.budgetDate = budgetDate;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public String getBudgetText() {
		return budgetText;
	}
	
	public void setBudgetText(String budgetText) {
		this.budgetText = budgetText;
	}

	public String getBudgetTextClass() {
		return budgetTextClass;
	}
	
	public void setBudgetTextClass(String budgetTextClass) {
		this.budgetTextClass = budgetTextClass;
	}
	
}
