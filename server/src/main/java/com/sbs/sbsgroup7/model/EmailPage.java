package com.sbs.sbsgroup7.model;

import javax.validation.constraints.*;

public class EmailPage {

    @NotNull(message="Required*")
    @DecimalMin(value= "1.00", message="Must be at least $1.00")
    @DecimalMax(value="99999.99", message="Cannot exceed $99,999.99")
    @Digits(integer=6, fraction=2, message="Please enter the correct amount of cents")
    private double amount;

    @NotNull(message="Required*")
    private Long fromAcc;

    @NotNull(message = "Required*")
    @NotEmpty(message = "Required*")
    @Email
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message="Email address is in incorrect form")
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
