package com.sbs.sbsgroup7.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "appointment")
public class Appointment {

    //Appointment ID
    @Id
    @Column(name = "appId",nullable = false)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String appId;


    //userID (Foreign Key)
    //@Column(name = "userId")
    //@NotNull
    //private String userId;

    //title
    @Column(name = "title")
    @NotNull
    private String title;

    //description
    @Column(name = "description")
    @NotNull
    private String description;

    //Start time
//    @Column(name = "startTime")
//    @NotNull
//    private Timestamp startTime;

    //End time
    //@Column(name = "endTime")
    //@NotNull
    //private Timestamp endTime;

    //Contact way
    @Column(name = "contactWay")
    @NotNull
    private String contactWay;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    @NotNull
    private User user;


    public Appointment(@JsonProperty("appId") String appId,
                       @JsonProperty("userId") String userId,
                       @JsonProperty("title") String title,
                       @JsonProperty("description") String description,
                       @JsonProperty("contactWay") String contactWay){
                       //@JsonProperty("user") User user){

        this.appId=appId;
        //this.userId=userId;
        this.title=title;
        this.description=description;
        this.contactWay=contactWay;
        //this.user = user;
    }

    public Appointment() { }

    public void setAppId(String appId){
        this.appId=appId;
    }

    public String getAppId() { return appId; }

   // public void setUserId(String userId){ this.userId=userId; }
    // public String getUserId() { return userId; }

    public void setTitle(String title) { this.title=title; }

    public String getTitle() { return title; }

    public void setDescription(String description){ this.description=description; }

    public String getDescription() { return description; }

    public void setContactWay(String contactWay){ this.contactWay=contactWay; }

    public String getContactWay() { return contactWay; }

    public void setUser(User user) { this.user=user; }

    public User getUser() { return user; }


}


