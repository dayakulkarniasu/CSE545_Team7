package com.sbs.sbsgroup7.model;

public class contact {

    private int id;
    private String name;
    private String emailId;
    private String address;
    private int phone;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getAddress() {
        return address;
    }

    public int getPhone() {
        return phone;
    }

    public contact(){

    }
    public contact(String name, String emailId, String address, int phone)
    {
        this.name=name;
        this.emailId=emailId;
        this.address=address;
        this.phone=phone;
    }

}
