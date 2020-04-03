package com.sbs.sbsgroup7.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name="transaction")
public class Transaction {

    @Id
    @Column(name = "transactionId",nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionId;

    @NotNull
    @Column(name = "transactionType",nullable = false)
    private String transactionType;

    @NotNull
    @Column(name = "transactionTime",nullable = false)
    private Instant transactionTime;

    public void setModifiedTime(Instant modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Instant getModifiedTime() {
        return modifiedTime;
    }

    @Column(name = "modifiedTime",nullable = true)
    private Instant modifiedTime;

    @NotNull
    @Min(1)
    @Column(name = "amount",nullable = false)
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "userId", nullable=false)
    @NotNull
    private User transactionOwner;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Account toAccount;

    @NotNull
    @Column(name = "transactionStatus",nullable = false)
    private String transactionStatus;

    @Column(name = "description",nullable = true)
    private String description;


    public Long getTransactionId() {
        return transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public Instant getTransactionTime() {
        return transactionTime;
    }

    public Double getAmount() {
        return amount;
    }

    public User getTransactionOwner() {
        return transactionOwner;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public String getDescription() {
        return description;
    }


    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setTransactionTime(Instant transactionTime) {
        this.transactionTime = transactionTime;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setTransactionOwner(User transactionOwner) {
        this.transactionOwner = transactionOwner;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
