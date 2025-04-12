package com.budget.app.entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="budget_dates")
@Component
public class BudgetDate
{

    // Database Setup
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_date_id")
    private int id;

    @Column(name = "budget_year")
    private int budgetYear;

    @Column(name = "budget_month")
    private String budgetMonth;

    @Column(name = "start_date")
    private LocalDate startDate;
//    private Date startDate;

    @Column(name = "end_date")
    private LocalDate endDate;
//    private Date endDate;


    // Connections
    @OneToMany(mappedBy = "budgetDate",cascade = CascadeType.ALL)
    private List<Budget> budgets = new ArrayList<Budget>();

    @Transient
    private boolean currentBudgetMonth = false;

    @Transient
    private boolean budgetSelected = false;


    // Constructors
    public BudgetDate() {}
    public BudgetDate(int id, int year, String month, LocalDate startDate, LocalDate endDate, List<Budget> budgets)
    {
        this.id = id;
        this.budgetYear = year;
        this.budgetMonth = month;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budgets = budgets;
    }


    // Getters & Setters
    public int getId() {return id;}

    public int getBudgetYear() {return budgetYear;}

    public String getBudgetMonth() {return budgetMonth;}

    public LocalDate getStartDate() {return startDate;}

    public LocalDate getEndDate() {return endDate;}

    public boolean getCurrentBudgetMonth() {return currentBudgetMonth;}

    public boolean getBudgetSelected() {return budgetSelected;}

    public void setId(int id) {this.id = id;}

    public void setBudgetYear(int year) {this.budgetYear = year;}

    public void setBudgetMonth(String month) {this.budgetMonth = month;}

    public void setStartDate(LocalDate startDate) {this.startDate = startDate;}

    public void setEndDate(LocalDate endDate) {this.endDate = endDate;}

    public List<Budget> getBudgets() {return budgets;}

    public void setBudgets(List<Budget> budgets) {this.budgets = budgets;}

    public void setCurrentBudgetMonth(boolean currentBudgetMonth) {this.currentBudgetMonth = currentBudgetMonth;}

    public void setBudgetSelected(boolean budgetSelected) {this.budgetSelected = budgetSelected;}

    @Override
    public String toString() {
        return "BudgetDate{" +
                "id=" + id +
                ", budgetYear=" + budgetYear +
                ", budgetMonth='" + budgetMonth + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", budgets=" + budgets +
                ", currentBudgetMonth=" + currentBudgetMonth +
                ", budgetSelected=" + budgetSelected +
                '}';
    }
}