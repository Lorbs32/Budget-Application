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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_item_id")
    private int id;

    @Column(name="line_item_name")
    private String lineItemName;

    @Column(name="planned_amount")
    private BigDecimal plannedAmount = BigDecimal.valueOf(0.00);

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(mappedBy = "lineItem",cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<Transaction>();

    public LineItem() {}

    public LineItem(int id, String lineItemName, BigDecimal plannedAmount, Group group, List<Transaction> transactions)
    {
        this.id = id;
        this.lineItemName = lineItemName;
        this.plannedAmount = plannedAmount;
        this.group = group;
        this.transactions = transactions;
    }

    public int getId() {return id;}

    public String getLineItemName() {return lineItemName;}

    public BigDecimal getPlannedAmount() {return plannedAmount;}

    public Group getGroup() {return group;}

    public List<Transaction> getTransactions() {return transactions;}

    public void setId(int id) {this.id = id;}

    public void setGroup(Group group) {this.group = group;}

    public void setLineItemName(String lineItemName) {this.lineItemName = lineItemName;}

    public void setPlannedAmount(BigDecimal plannedAmount) {this.plannedAmount = plannedAmount;}

    public void setTransactions(List<Transaction> transactions) {this.transactions = transactions;}
}