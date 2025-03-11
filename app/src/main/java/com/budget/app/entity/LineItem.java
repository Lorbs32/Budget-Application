package com.budget.app.entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="line_items")
@Component
public class LineItem
{

    // Database setup
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_item_id")
    private int id;

    @Column(name="line_item_name")
    private String lineItemName; // Example: "Electric Bill", "Paycheck"

    @Column(name="planned_amount")
    private BigDecimal plannedAmount = BigDecimal.valueOf(0.00); // Example: 100.50

    @Column(name="is_income")
    private boolean isIncome; // True for income, False for expenses


    // Connections
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "group_id")
    private Category category;
    @OneToMany(mappedBy = "lineItem",cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<Transaction>();


    // Constructors
    public LineItem() {}
    public LineItem(String lineItemName, BigDecimal plannedAmount,
                    Boolean isIncome, Category category, List<Transaction> transactions)
    {
        this.lineItemName = lineItemName;
        this.plannedAmount = plannedAmount;
        this.isIncome = isIncome;
        this.category = category;
        this.transactions = transactions;
    }


    // Getters & Setters
    public int getId() {return id;}

    public String getLineItemName() {return lineItemName;}

    public BigDecimal getPlannedAmount() {return plannedAmount;}

    public Category getCategory() {return category;}

    public List<Transaction> getTransactions() {return transactions;}

    public boolean isIncome() {return isIncome;}

    public void setIncome(boolean income) {isIncome = income;}

    public void setId(int id) {this.id = id;}

    public void setCategory(Category category) {this.category = category;}

    public void setLineItemName(String lineItemName) {this.lineItemName = lineItemName;}

    public void setPlannedAmount(BigDecimal plannedAmount) {this.plannedAmount = plannedAmount;}

    public void setTransactions(List<Transaction> transactions) {this.transactions = transactions;}
}