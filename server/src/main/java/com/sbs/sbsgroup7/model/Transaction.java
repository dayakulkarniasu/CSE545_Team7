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


    public  Transaction(@JsonProperty("transactionID") String transactionID){
        this.transactionID  =transactionID;

    }

    public String getTID() { return transactionID; }


}
