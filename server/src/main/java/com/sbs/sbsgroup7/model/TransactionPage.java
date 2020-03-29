package com.sbs.sbsgroup7.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class TransactionPage {

    @Min(1)
    @NotNull
    private double amount;

    @NotNull
    private Long fromAcc;

    @NotNull
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
