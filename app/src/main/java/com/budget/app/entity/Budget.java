package com.budget.app.entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="budgets")
@Component
public class Budget
{

    // Database Setup
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_id")
    private int id;


    // Connections
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "date_id")
    private BudgetDate budgetDate;
    @OneToMany(mappedBy = "budget",cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<Category>();


    // Constructors
    public Budget() {}
    public Budget(int id, User user, BudgetDate date, List<Category> categories)
    {
        this.id = id;
        this.user = user;
        this.budgetDate = date;
        this.categories = categories;
    }


    // Getters & Setters
    public int getId() {return id;}

    public User getUser() {return user;}

    public BudgetDate getBudgetDate() {return budgetDate;}

    public List<Category> getCategories() {return categories;}

    public void setId(int id) {this.id = id;}

    public void setUser(User user) {this.user = user;}

    public void setBudgetDate(BudgetDate budgetDate) {this.budgetDate = budgetDate;}

    public void setCategories(List<Category> categories) {this.categories = categories;}
}