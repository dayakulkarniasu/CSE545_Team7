package com.sbs.sbsgroup7.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class EmailPage {

    @Min(1)
    @NotNull
    private double amount;

    @NotNull
    private Long fromAcc;

    @NotNull
    @Email
    private String emailId;

    private String description;

    public EmailPage(){}

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setFromAcc(Long fromAcc) {
        this.fromAcc = fromAcc;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Long getFromAcc() {
        return fromAcc;
    }


    public String getDescription() {
        return description;
    }
}
