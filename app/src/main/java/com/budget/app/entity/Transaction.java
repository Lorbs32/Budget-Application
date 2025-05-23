package com.budget.app.entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name ="transactions")
@Component
public class Transaction
{

    // Database Setup
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int id;

    @Column(name="merchant")
    private String merchant;

    @Column(name="actual_amount")
    private BigDecimal actualAmount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    // BigDecimal.valueof(0.00);

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "note")
    private String note;


    // Connections
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "line_item_id")
    private LineItem lineItem;


    // Constructors
    public Transaction() {}
    public Transaction(int id, String merchant, BigDecimal actualAmount, LocalDate transactionDate, String note, LineItem lineItem)
    {
        this.id = id;
        this.merchant = merchant;
        this.actualAmount = actualAmount;
        this.transactionDate = transactionDate;
        this.note = note;
        this.lineItem = lineItem;
    }


    // Getters & Setters
    public int getId() {return id;}

    public String getMerchant() {return merchant;}

    public BigDecimal getActualAmount() {return actualAmount;}

    public LocalDate getTransactionDate() {return transactionDate;}

    public String getNote() {return note;}

    public LineItem getLineItem() {return lineItem;}

    public void setId(int id) {this.id = id;}

    public void setMerchant(String merchant) {this.merchant = merchant;}

    public void setActualAmount(BigDecimal actualAmount) {this.actualAmount = actualAmount;}

    public void setTransactionDate(LocalDate transactionDate) {this.transactionDate = transactionDate;}

    public void setNote(String note) {this.note = note;}

    public void setLineItem(LineItem lineItem) {this.lineItem = lineItem;}
}