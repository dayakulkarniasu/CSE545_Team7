package com.sbs.sbsgroup7.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CashierCheque
{
    @Min(1)
    @NotNull
    private double amount;

    @NotNull
    private Long checkNumber;

    @NotNull
    private Long toAcc;

    private String description;

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCheckNumber(Long checkNumber) {
        this.checkNumber = checkNumber;
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

    public Long getCheckNumber() {
        return checkNumber;
    }

    public Long getToAcc() {
        return toAcc;
    }

    public String getDescription() {
        return description;
    }
}
