package com.budget.app.entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="budget_dates")
@Component
public class BudgetDate
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_date_id")
    private int id;

    @Column(name = "budget_year")
    private int budgetYear;

    @Column(name = "budget_month")
    private String budgetMonth;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @OneToMany(mappedBy = "budgetDate",cascade = CascadeType.ALL)
    //@OneToOne(mappedBy = "budgetDate")
    private List<Budget> budgets = new ArrayList<Budget>();

    public BudgetDate() {}

    public BudgetDate(int id, int year, String month, Date startDate, Date endDate, List<Budget> budgets)
    {
        this.id = id;
        this.budgetYear = year;
        this.budgetMonth = month;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budgets = budgets;
    }

    public int getId() {return id;}

    public int getBudgetYear() {return budgetYear;}

    public String getBudgetMonth() {return budgetMonth;}

    public Date getStartDate() {return startDate;}

    public Date getEndDate() {return endDate;}

    public void setId(int id) {this.id = id;}

    public void setBudgetYear(int year) {this.budgetYear = year;}

    public void setBudgetMonth(String month) {this.budgetMonth = month;}

    public void setStartDate(Date startDate) {this.startDate = startDate;}

    public void setEndDate(Date endDate) {this.endDate = endDate;}

    public List<Budget> getBudgets() {return budgets;}

    public void setBudgets(List<Budget> budgets) {this.budgets = budgets;}
}