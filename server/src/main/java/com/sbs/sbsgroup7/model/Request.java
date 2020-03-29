package com.sbs.sbsgroup7.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "request")
public class Request {
    @Id
    @Column(name = "requestId",nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long requestId;

//    @Column(name = "createdTime")
//    @NotNull
//    private Timestamp createdTime;

    @Column(name = "status")
    @NotNull
    private String status;

    @Column(name = "requestedUser")
    @NotNull
    private String requestedUser;

    @Column(name = "approvedUser")
    @NotNull
    private String approvedUser;

    @Column(name = "sourceAccount")
    @NotNull
    private String sourceAccount;

    @Column(name = "description")
    @NotNull
    private String description;

    @Column(name = "requestType")
    @NotNull
    private String requestType;

    public Request(@JsonProperty("requestId") long requestId,
                   @JsonProperty("status") String status,
                   @JsonProperty("requestedUser") String requestedUser,
                   @JsonProperty("approvedUser") String approvedUser,
                   @JsonProperty("sourceAccount") String sourceAccount,
                   @JsonProperty("description") String description,
                   @JsonProperty("requestType") String requestType){
        this.requestId=requestId;
        this.status=status;
        this.requestedUser = requestedUser;
        this.approvedUser=approvedUser;
        this.sourceAccount=sourceAccount;
        this.description=description;
        this.requestType=requestType;
    }

    public Request(){

    }

    public long getRequestId() {
        return requestId;
    }

    public String getStatus() {
        return status;
    }

    public String getRequestedUser() { return requestedUser; }

    public String getApprovedUser() { return approvedUser; }

    public String getSourceAccount() { return sourceAccount; }

    public String getDescription() { return description; }

    public String getRequestType() { return requestType; }


    public void setRequestId(long requestId) {
        this.requestId=requestId;
    }

    public void setStatus(String status) {
        this.status=status;
    }

    public void setRequestedUser(String requestedUser) { this.requestedUser=requestedUser; }

    public void setApprovedUser(String approvedUser) { this.approvedUser=approvedUser; }

    public void setSourceAccount(String sourceAccount) { this.sourceAccount=sourceAccount; }

    public void setDescription(String description) { this.description=description; }

    public void setRequestType(String requestType) { this.requestType=requestType; }
}
