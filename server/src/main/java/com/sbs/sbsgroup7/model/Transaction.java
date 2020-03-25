package com.sbs.sbsgroup7.model;



import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


import java.util.UUID;

@Entity
public class Transaction {

    @Id
    @Column(name = "transactionID",nullable = false)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String transactionID;

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

    public  Transaction(@JsonProperty("transactionID") String transactionID,
                        @JsonProperty("transactionID") double amount,
                        @JsonProperty("transactionID") int createdTime,
                        @JsonProperty("transactionID") String status,
                        @JsonProperty("transactionID") int commitTime,
                        @JsonProperty("transactionID") String description){
        this.transactionID  =transactionID;
        this.amount  =amount;
        this.createdTime  =createdTime;
        this.status  =status;
        this.commitTime  =commitTime;
        this.description  =description;

    }

    public String getTID() { return transactionID; }

    public void setTID(String transactionID){
        this.transactionID=transactionID;
    }
    public double getAmount() { return amount; }

    public void setTID(double amount){
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


}
