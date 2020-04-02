package com.sbs.sbsgroup7.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "appointment")
public class Appointment {

    //Appointment ID
    @Id
    @Column(name = "appId",nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long appId;


    //userID (Foreign Key)
    @JoinColumn(name = "userId", nullable=false)
    @NotNull
    @OneToOne
    private User user;

    //title
    @Column(name = "title")
    @NotNull
    private String title;

    //description
    @Column(name = "description")
    private String description;

    // Start time
    @Column(name = "startTime")
    @NotNull
    private String startTime;

    //End time
    @Column(name = "endTime")
    @NotNull
    private String endTime;

    //Contact way
    @Column(name = "contactWay")
    @NotNull
    private String contactWay;

    public Appointment(@JsonProperty("appId") Long appId,
                       @JsonProperty("user") User user,
                       @JsonProperty("title") String title,
                       @JsonProperty("description") String description,
                       @JsonProperty("contactWay") String contactWay){
        this.appId=appId;
        this.user=user;
        this.title=title;
        this.description=description;
        this.contactWay=contactWay;
    }

    public Appointment() { }

    public void setAppId(Long appId){
        this.appId=appId;
    }

    public Long getAppId() { return appId; }

    public void setTitle(String title) { this.title=title; }

    public String getTitle() { return title; }

    public void setDescription(String description){ this.description=description; }

    public String getDescription() { return description; }

    public void setContactWay(String contactWay){ this.contactWay=contactWay; }

    public String getContactWay() { return contactWay; }


    public void setUser(User user) { this.user=user; }

    public User getUser() { return user; }

    public String getStartTime() { return startTime ;}

    public void setStartTime(String startTime){ this.startTime = startTime; }

    public String getEndTime() { return endTime ;}

    public void setEndTime(String endTime){ this.endTime = endTime; }
}

