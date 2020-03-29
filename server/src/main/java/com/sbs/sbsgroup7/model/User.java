package com.sbs.sbsgroup7.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "userId",nullable = false)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String userId;

    @Column(name = "firstName")
    @NotNull
    private String firstName;

    @Column(name = "lastName")
    @NotNull
    private String lastName;

    @Column(name = "email")
    @NotNull
    @Email
    private String email;

    @Column(name = "phone")
    @NotNull
    private String phone;

    @Column(name = "roles")
    @NotNull
    private String role;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "ssn" , unique = true)
    @NotNull
    private String ssn;

    @Column(name = "dob")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date dob;

    @Column(name = "address")
    @NotNull
    private String address;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private List<SessionLog> sessionLog;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Account> accounts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "requestedUser")
    private List<Request> requests;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transactionOwner")
    private List<Transaction> transactions;

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public boolean isActive() {
        return active;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }



    public List<Request> getRequests() {
        return requests;

    }
//    @OneToMany(cascade = CascadeType.ALL, targetEntity = Request.class)
//    @JoinColumn(name = "userId")
//    private List<Request> requests;


    private boolean active;


    public User(@JsonProperty("userId") String userId,
                @JsonProperty("firstName") String firstName,
                @JsonProperty("lastName") String lastName,
                @JsonProperty("email") String email,
                @JsonProperty("phone") String phone,
                @JsonProperty("role") String role,
                @JsonProperty("password") String password,
                @JsonProperty("ssn") String ssn,
                @JsonProperty("dob") Date dob,
                @JsonProperty("address") String address,
                @JsonProperty("active") boolean active
                ){
        this.userId=userId;
        this.firstName=firstName;
        this.lastName = lastName;
        this.email=email;
        this.phone=phone;
        this.role=role;
        this.password=password;
        this.ssn=ssn;
        this.dob=dob;
        this.address=address;
        this.active=true;

    }

    public User(){

    }

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() { return lastName; }

    public String getEmail() { return email; }

    public String getPhone() { return phone; }

    public String getRole() { return role; }

    public String getUserName() { return email; }

    public String getPassword() { return password; }

    public String getSsn() { return ssn; }

    public Date getDob() { return dob; }

    public String getAddress()  { return address; }

    public boolean getActive(){ return active; }

    public List<SessionLog> getSessionLog() {
        return sessionLog;
    }

    public List<Account> getAccounts() {
        return accounts;
    }





    public void setUserId(String userId) {
         this.userId=userId;
    }

    public void setFirstName(String firstName) {
        this.firstName=firstName;
    }

    public void setLastName(String lastName) { this.lastName=lastName; }

    public void setEmail(String email) { this.email=email; }

    public void setPhone(String phone) { this.phone= phone; }

    public void setRole(String role) { this.role=role; }


    public void setPassword(String password) { this.password = password; }

    public void setSsn(String ssn) { this.ssn=ssn; }

    public void setDob(Date dob) { this.dob=dob; }

    public void setAddress(String address)  { this.address=address; }

    public void setActive(boolean active){ this.active=active; }

    public void setSessionLog(List<SessionLog> sessionLog) {
        this.sessionLog = sessionLog;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }


}
