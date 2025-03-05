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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_id")
    private int id;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "date_id")
    private BudgetDate budgetDate;

//    @OneToMany(mappedBy = "budget",cascade = CascadeType.ALL)
//    private List<LineItem> lineItems = new ArrayList<LineItem>();

    @OneToMany(mappedBy = "budget",cascade = CascadeType.ALL)
    private List<Group> groups = new ArrayList<Group>();

    public Budget() {}

    public Budget(int id, User user, BudgetDate date, List<Group> groups)
    {
        this.id = id;
        this.user = user;
        this.budgetDate = date;
        //this.lineItems = lineItems;
        this.groups = groups;
    }

    public int getId() {return id;}

    public User getUser() {return user;}

    public BudgetDate getBudgetDate() {return budgetDate;}

    //public List<LineItem> getLineItems() {return lineItems;}

    public List<Group> getGroups() {return groups;}

    public void setId(int id) {this.id = id;}

    public void setUser(User user) {this.user = user;}

    public void setBudgetDate(BudgetDate budgetDate) {this.budgetDate = budgetDate;}

    //public void setLineItems(List<LineItem> lineItems) {this.lineItems = lineItems;}

    public void setGroups(List<Group> groups) {this.groups = groups;}
}