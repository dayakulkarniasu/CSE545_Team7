package com.sbs.sbsgroup7.model;

import javax.validation.constraints.*;

public class TransactionPage {

    @NotNull(message="Required*")
    @DecimalMin(value= "1.00", message="Must be at least $1.00")
    @DecimalMax(value="99999.99", message="Cannot exceed $99,999.99")
    @Digits(integer=6, fraction=2, message="Please enter the correct amount of cents")
    private double amount;

    @NotNull(message="Required*")
    private Long fromAcc;

    @NotNull(message="Required*")
    private Long toAcc;

    private String description;

    public TransactionPage(){}

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setFromAcc(Long fromAcc) {
        this.fromAcc = fromAcc;
    }

    public void setToAcc(Long toAcc) {
        this.toAcc = toAcc;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public Long getFromAcc() {
        return fromAcc;
    }

    public Long getToAcc() {
        return toAcc;
    }

    public String getDescription() {
        return description;
    }
}
