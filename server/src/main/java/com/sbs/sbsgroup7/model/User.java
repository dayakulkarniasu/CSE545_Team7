package com.sbs.sbsgroup7.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "userId",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "firstName", length=50)
    @NotNull
    @Size(min=1, max=50)
    private String firstName;

    @Column(name = "lastName", length=50)
    @NotNull
    @Size(min=1,max=50)
    private String lastName;

    @Column(name = "email",unique = true)
    @NotNull
    @Email
    private String email;

    @Column(name = "phone", unique = true)
    @NotNull
    private int phone;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "ssn")
    @NotNull
    private int ssn;

    @Column(name = "dob")
    @NotNull
    private Date dob;

    @Column(name = "address")
    @NotNull
    private String address;

    @Column(nullable = false, length=32)
    private String userRole;

    @NotNull
    @Size(min=60, max=60)
    @Column(nullable=false, length=60)
    private String passwordHash;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "user")
    private Set<Account> accounts= new HashSet<>();


}
