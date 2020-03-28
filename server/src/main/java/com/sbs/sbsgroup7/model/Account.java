package com.sbs.sbsgroup7.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @Column(name = "accountNumber",nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
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
        this.accountNumber=accountNumber;
    }

    public void setUser(User user) {
        this.user=user;
    }

    public void setAccountType(String accountType) { this.accountType=accountType; }

    public void setBalance(double balance) { this.balance=balance; }
}
