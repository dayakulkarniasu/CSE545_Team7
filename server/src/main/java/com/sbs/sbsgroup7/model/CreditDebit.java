package com.sbs.sbsgroup7.model;

import javax.validation.constraints.*;

public class CreditDebit {

    @NotNull(message="Required*")
    private Long accountNumber;

    @NotNull(message="Required*")
    @DecimalMin(value= "1.00", message="Must be at least $1.00")
    @DecimalMax(value="99999.99", message="Cannot exceed $99,999.99")
    @Digits(integer=6, fraction=2, message="Please enter the correct amount of cents")
    private Double amount;

    @NotNull(message="Required*")
    @NotEmpty(message = "Required*")
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
