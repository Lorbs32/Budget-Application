package com.budget.app.entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="categories")
@Component
public class Category
{

    // Database Setup
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int id;

    @Column(name = "category_name")
    private String categoryName;


    // Connections
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "budget_id")
    private Budget budget;
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<LineItem> lineItems = new ArrayList<LineItem>();


    // Constructors
    public Category() {}
    public Category(int id, String categoryName, Budget budget, List<LineItem> lineItems)
    {
        this.id = id;
        this.categoryName = categoryName;
        this.budget = budget;
        this.lineItems = lineItems;
    }


    // Getters & Setters
    public int getId() {return id;}

    public String getGroupName() {return categoryName;}

    public Budget getBudget() {return budget;}

    public List<LineItem> getLineItems() {return lineItems;}

    public void setId(int id) {this.id = id;}

    public void setGroupName(String groupName) {this.categoryName = groupName;}

    public void setBudget(Budget budget) {this.budget = budget;}

    public void setLineItems(List<LineItem> lineItems) {this.lineItems = lineItems;}
}
