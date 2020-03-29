package com.sbs.sbsgroup7.model;

import javax.validation.constraints.NotNull;

public class CreditDebit {

    @NotNull
    private Long accountNumber;

    @NotNull
    private Double amount;

    @NotNull
    private String transferType;

    private String description;

    public Double getAmount() {
        return amount;
    }

    public String getTransferType() {
        return transferType;
    }

    public String getDescription() {
        return description;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }


}
