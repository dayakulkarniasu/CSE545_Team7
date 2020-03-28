package com.sbs.sbsgroup7.model;



import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


import java.util.List;
import java.util.UUID;

@Entity
public class Transaction {

    @Id
    @Column(name = "transactionID",nullable = false)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String transactionID;

    @Column(name = "srcAcct")
    @NotNull
    private Long srcAcct;

    @Column(name = "dstAcct")
    @NotNull
    private Long dstAcct;

    @Column(name = "amount")
    @NotNull
    private double amount;

    @Column(name = "createdTime")
    @NotNull
    private int createdTime;

    @Column(name = "status")
    @NotNull
    private String status;

    @Column(name = "commitTime")
    @NotNull
    private int commitTime;

    @Column(name = "description")
    @NotNull
    private String description;
/*
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "srcAcct", referencedColumnName = "accountNumber")
    private List<SessionLog> sessionLog;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Account.class)
    @JoinColumn(name = "srcAcct")
    private List<Account> accounts;
*/


    public Transaction(){}
    public  Transaction(@JsonProperty("srcAcct") Long srcAcct,
                        @JsonProperty("dstAcct") Long dstAcct,
                        @JsonProperty("amount") double amount){
        this.srcAcct  = srcAcct;
        this.dstAcct = dstAcct;
        this.amount  = amount;
        /*
        this.createdTime  =createdTime;
        this.status  =status;
        this.commitTime  =commitTime;
        this.description  =description;
        */
    }

    public String getTID() { return transactionID; }

    public void setTID(String transactionID){
        this.transactionID=transactionID;
    }

    public Long getSrcAcct() { return srcAcct;}

    public void setSrcAcct(Long srcAcct) {
        this.srcAcct = srcAcct;
    }

    public Long getDstAcct() { return dstAcct;}

    public void setDstAcct(Long dstAcct) {
        this.dstAcct = dstAcct;
    }

    public double getAmount() { return amount; }

    public void setAmount(double amount){
        this.amount=amount;
    }
    public double getCreateTime() { return createdTime; }

    public void setCreatedTime(int createdTime){
        this.createdTime=createdTime;
    }
    public int getCommitTime (){ return commitTime; }

    public void setCommitTime(int commitTime){
        this.commitTime=commitTime;
    }
    public String getStatus(){ return status; }

    public void setStatus(String status){
        this.status=status;
    }

    public String getDescription(){ return description; }

    public void setDescription(String description){
        this.description=description;
    }

    /*public void setSessionLog(List<SessionLog> sessionLog) {
        this.sessionLog = sessionLog;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }*/
}
