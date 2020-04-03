package com.sbs.sbsgroup7.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "userId",nullable = false)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String userId;

    @Column(name = "firstName")
    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Required*")
    @Size(min=2, max=30, message="First name must be between 2 and 30 characters")
    private String firstName;

    @Column(name = "lastName")
    @NotNull(message = "Required*")
    @NotEmpty(message = "Required*")
    @Size(min=2, max=30, message="Last name must be between 2 and 30 characters")
    private String lastName;

    @Column(name = "email")
    @NotNull(message = "Required*")
    @NotEmpty(message = "Required*")
    @Email
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message="Email address is invalid")
    private String email;

    @Column(name = "phone")
    @NotNull(message = "Required*")
    @NotEmpty(message = "Required*")
    @Pattern(regexp="^[0-9][0-9]{2}-[0-9]{3}-[0-9]{4}$", message="Phone numbers must be in this format: 480-123-4567")
    private String phone;

    @Column(name = "roles")
    @NotNull(message = "Required*")
    @NotEmpty(message = "Please select a role")
    private String role;

    @Column(name = "password")
    @NotNull(message = "Required*")
    @NotEmpty(message = "Required*")
    @Size(min=6, max=60, message="Password must be at least 6 characters long")
    private String password;

    @Column(name = "ssn" , unique = true)
    @NotNull(message = "Required*")
    @NotEmpty(message = "Required*")
    @Pattern(regexp = "^[0-9][0-9]{2}-[0-9]{2}-[0-9]{4}$", message="SSN must use numbers in this format: XXX-YY-ZZZZ")
    private String ssn;

    @Column(name = "dob")
    @NotNull(message = "Required*")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Past
    private Date dob;

    @Column(name = "address")
    @NotNull(message = "Required*")
    @NotEmpty(message = "Required*")
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

    @OneToOne(mappedBy = "user")
    private Appointment appointment;

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Appointment getAppointment() {
        return appointment;
    }

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
