package com.sbs.sbsgroup7.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @Column(name = "accountNumber",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNumber;

    @ManyToOne
    @JoinColumn(name = "userId", nullable=false)
    @NotNull
    @JsonIgnore
    private User user;

    @Column(name = "accountType")
    @NotNull
    private String accountType;

    @Column(name = "balance")
    @NotNull
    private double balance;

    @OneToMany(mappedBy = "fromAccount")
    private List<Transaction> debitTransactions;

    @OneToMany(mappedBy = "toAccount")
    private List<Transaction> creditTransactions;

    @OneToMany(mappedBy = "account")
    private List<Request> requests;

    @OneToMany(mappedBy="account")
    private List<Cheque> cheque;

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public void setCheque(List<Cheque> cheque) {
        this.cheque = cheque;
    }

    public List<Cheque> getCheque() {
        return cheque;
    }

    public void setDebitTransactions(List<Transaction> debitTransactions) {
        this.debitTransactions = debitTransactions;
    }

    public void setCreditTransactions(List<Transaction> creditTransactions) {
        this.creditTransactions = creditTransactions;
    }

    public List<Transaction> getDebitTransactions() {
        return debitTransactions;
    }

    public List<Transaction> getCreditTransactions() {
        return creditTransactions;
    }

    public Account(@JsonProperty("accountNumber") Long accountNumber,
                   @JsonProperty("accountType") String accountType,
                   @JsonProperty("balance") double balance){
        this.accountNumber=accountNumber;
        this.accountType = accountType;
        this.balance=balance;
    }

    public Account(){

    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public User getUser() {
        return user;
    }

    public String getAccountType() { return accountType; }

    public double getBalance() { return balance; }


    public void setAccountNumber(Long accountNumber) {
        this.accountNumber= accountNumber ;
    }

    public void setUser(User user) {
        this.user=user;
    }

    public void setAccountType(String accountType) { this.accountType=accountType; }

    public void setBalance(double balance) { this.balance=balance; }
}
