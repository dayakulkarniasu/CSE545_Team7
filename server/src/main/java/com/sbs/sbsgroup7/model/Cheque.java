package com.sbs.sbsgroup7.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name="cheque")
public class Cheque {

    @Id
    @Column(name = "checkNumber",nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long checkNumber;

    @ManyToOne
    @JoinColumn(name = "accountNumber", nullable=false)
    @NotNull
    private Account account;

    @Column(name = "requestedTime",nullable = false)
    @NotNull
    private Instant requestedTime;

    @Column(name="modifiedTime", nullable = true)
    private Instant modifiedTime;

    @Column(name="active",nullable =false)
    private Boolean active;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setCheckNumber(Long checkNumber) {
        this.checkNumber = checkNumber;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


    public Long getCheckNumber() {
        return checkNumber;
    }

    public Account getAccount() {
        return account;
    }

    public void setRequestedTime(Instant requestedTime) {
        this.requestedTime = requestedTime;
    }

    public void setModifiedTime(Instant modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Instant getRequestedTime() {
        return requestedTime;
    }

    public Instant getModifiedTime() {
        return modifiedTime;
    }
}
