package com.sbs.sbsgroup7.model;



import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Account {

    @Id
    @Column(name = "accountNumber",nullable = false)
//    @GeneratedValue(generator="system-uuid")
//    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountNumber;

    @JoinColumn(nullable = false)
    @NotNull
    @ManyToOne
    private User user;

    @Column(name="accountType")
    @NotNull
    private String accountType;

    @Column(name="balance")
    private double balance;

//    @Column(name="rateOfInterest")
//    private double rateOfInterest;

    public Account(@JsonProperty("accountNumber") Integer accountNumber,
                @JsonProperty("accountType") String accountType,
                @JsonProperty("balance") double balance) {
//                @JsonProperty("rateOfInterest") double rateOfInterest){

        this.accountNumber=accountNumber;
        this.accountType=accountType;
        this.balance=balance;
//        this.rateOfInterest=rateOfInterest;

    }

    public Account() {

    }

    public void setAccountNumber(Integer accountNumber) { this.accountNumber=accountNumber; }

    public Integer getAccountNumber() { return accountNumber; }

    public void setAccountType(String accountType){
        this.accountType=accountType;
    }

    public String getAccountType() { return accountType; }

    public void setBalance(double balance){
        this.balance=balance;
    }

    public double getBalance() { return balance; }

    public void setUser(User user) { this.user=user; }

    public User getUser() { return user; }

//    public void setRateOfInterest(double rateOfInterest){
//        this.rateOfInterest=rateOfInterest;
//    }
//
//    public double getRateOfInterest() { return rateOfInterest; }



}
