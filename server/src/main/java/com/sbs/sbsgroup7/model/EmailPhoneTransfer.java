package com.sbs.sbsgroup7.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;


public class EmailPhoneTransfer {
    @Column(name = "arcEmail")
    @NotNull
    private String arcEmail;

    @Column(name = "desEmail")
    @NotNull
    private String desEmail;

    @Column(name = "amtEmail")
    @NotNull
    private double amtEmail;

    public EmailPhoneTransfer(){}
    public  EmailPhoneTransfer(@JsonProperty("arcEmail") String arcEmail,
                               @JsonProperty("desEmail") String desEmail,
                               @JsonProperty("amtEmail") double amtEmail){
        this.arcEmail  = arcEmail;
        this.desEmail = desEmail;
        this.amtEmail  = amtEmail;
    }

    public String getArcEmail() { return arcEmail; }

    public void setArcEmail(String arcEmail){
        this.arcEmail=arcEmail;
    }

    public String getDesEmail() { return desEmail; }

    public void setDesEmail(String desEmail){
        this.desEmail=desEmail;
    }

    public double getAmtEmail() { return amtEmail; }

    public void setAmtEmail(double amtEmail){
        this.amtEmail=amtEmail;
    }

}