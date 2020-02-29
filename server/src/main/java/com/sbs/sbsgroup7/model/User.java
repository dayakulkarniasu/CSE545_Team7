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
    private String userId; // Email id

    @Column(name = "firstName")
    @NotNull
    private String firstName;
    /*@Column(name = "lastName")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private int phone;
    @Column(name = "role")
    private String role;
    @Column(name = "password")
    private String password;
    @Column(name = "ssn")
    private int ssn;
    @Column(name = "dob")
    private Date dob;
    @Column(name = "address")
    private String address;*/

    public User(@JsonProperty("userId") String userId,
                @JsonProperty("firstName") String firstName){
        this.userId=userId;
        this.firstName=firstName;
    }

    public User(){

    }

    public String getId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }
}
