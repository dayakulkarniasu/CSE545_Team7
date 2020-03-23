package com.sbs.sbsgroup7.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
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
    private String email;

    @Column(name = "phone")
    @NotNull
    private int phone;

    @Column(name = "role")
    @NotNull
    private String role;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "ssn")
    @NotNull
    private int ssn;

//    @Column(name = "dob")
//    @NotNull
//    private Date dob;

    @Column(name = "address")
    @NotNull
    private String address;

    @Column(name = "city")
    @NotNull
    private String city;

    @Column(name = "state")
    @NotNull
    private String state;

    @Column(name = "country")
    @NotNull
    private String country;

    @Column(name = "zipCode")
    @NotNull
    private int zipCode;

    private boolean active;


    public User(@JsonProperty("userId") String userId,
                @JsonProperty("firstName") String firstName,
                @JsonProperty("lastName") String lastName,
                @JsonProperty("email") String email,
                @JsonProperty("phone") int phone,
                @JsonProperty("role") String role,
                @JsonProperty("password") String password,
                @JsonProperty("ssn") int ssn,
//                @JsonProperty("dob") Date dob,
                @JsonProperty("address") String address,
                @JsonProperty("city") String city,
                @JsonProperty("state") String state,
                @JsonProperty("country") String country,
                @JsonProperty("zipCode") int zipCode,
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
//        this.dob=dob;
        this.address=address;
        this.city=city;
        this.state=state;
        this.country=country;
        this.zipCode=zipCode;
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

    public int getPhone() { return phone; }

    public String getRole() { return role; }

    public String getUserName() { return email; }

    public String getPassword() { return password; }

    public int getSsn() { return ssn; }

//    public Date getDob() { return dob; }

    public String getAddress()  { return address; }

    public String getCity() { return city; }

    public String getState() { return state; }

    public String getCountry() { return country; }

    public int getZipCode() { return zipCode; }

    public boolean getActive(){ return active; }

}
