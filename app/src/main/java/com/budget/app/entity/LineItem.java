package com.budget.app.entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private BigDecimal plannedAmount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    //BigDecimal.valueOf(0.00); // Example: 100.50

    @Column(name="is_income")
    private boolean isIncome; // True for income, False for expenses

    @Enumerated(EnumType.STRING) // Example: ONE_TIME, WEEKLY, BI_WEEKLY, MONTHLY, YEARLY
    @Column(name = "recurrence")
    private RecurrenceType recurrenceType;


    // Connections
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(mappedBy = "lineItem",cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<Transaction>();

    // The summed amount of all transactions for this line item.
    @Transient
    private BigDecimal cumulativeActualAmount;


    // Constructors
    public LineItem() {}
    public LineItem(String lineItemName, BigDecimal plannedAmount,
                    Boolean isIncome, Category category, List<Transaction> transactions,
                    RecurrenceType recurrenceType)
    {
        this.lineItemName = lineItemName;
        this.plannedAmount = plannedAmount;
        this.isIncome = isIncome;
        this.category = category;
        this.transactions = transactions;
        this.recurrenceType = recurrenceType;
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

    public RecurrenceType getRecurrenceType() {return recurrenceType;}

    public void setRecurrenceType(RecurrenceType recurrenceType) {this.recurrenceType = recurrenceType;}

    public BigDecimal getCumulativeActualAmount() {return cumulativeActualAmount;}

    public void setCumulativeActualAmount(BigDecimal actualAmount) {this.cumulativeActualAmount = actualAmount;}
}