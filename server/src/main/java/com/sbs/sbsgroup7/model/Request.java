package com.sbs.sbsgroup7.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "request")
public class Request {

    @Id
    @Column(name = "requestId",nullable = false)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String requestId;

    //created time
//    @Column(name = "createdTime")
//    @NotNull
//    private Timestamp createdTime;

    @Column(name = "status")
    @NotNull
    private String status;

    //requestUser
    @Column(name = "requestedUser")
    @NotNull
    private String requestedUser;

    //requestUser
    @Column(name = "approvedUser")
    @NotNull
    private String approvedUser;

    @Column(name = "srcAcct")
    @NotNull
    private String srcAcct;

    @Column(name = "description")
    @NotNull
    private String description;

    @Column(name = "requestType")
    @NotNull
    private String requestType;

    public Request(@JsonProperty("requestId") String requestId,
                   @JsonProperty("status") String status,
                   @JsonProperty("requestedUser") String requestedUser,
                   @JsonProperty("approvedUser") String approvedUser,
                   @JsonProperty("srcAcct") String srcAcct,
                   @JsonProperty("description") String description,
                   @JsonProperty("requestType") String requestType) {

        this.requestId=requestId;
        this.status=status;
        this.requestedUser=requestedUser;
        this.approvedUser=approvedUser;
        this.srcAcct=srcAcct;
        this.description=description;
        this.requestType=requestType;
    }

    public Request() { }

    public void setRequestId(String requestId){
        this.requestId=requestId;
    }

    public String getRequestId() { return requestId; }

    public void setStatus(String status){
        this.status=status;
    }

    public String getStatus() { return status; }

    public void setRequestedUser() { this.requestedUser=requestedUser; }

    public String getRequestedUser() { return requestedUser; }

    public void setApprovedUser() { this.approvedUser=approvedUser; }

    public String getApprovedUser() { return approvedUser; }

    public void setSrcAcct() { this.srcAcct=srcAcct; }

    public String getSrcAcct() { return srcAcct; }

    public void setDescription(String description){ this.description=description; }

    public String getDescription() { return description; }

    public void setRequestType(String requestType){ this.requestType=requestType; }

    public String getRequestType() { return requestType; }
}
