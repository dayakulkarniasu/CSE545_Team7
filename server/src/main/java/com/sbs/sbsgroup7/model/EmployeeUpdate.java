package com.sbs.sbsgroup7.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "employee_updates")
public class EmployeeUpdate {

    @Id
    @Column
    private String userId;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private String ssn;

    @Column
    private String address;

    @Column
    private Date date;

    public EmployeeUpdate(String userId, String email, String phone, String ssn, String address, Date date) {
        this.userId = userId;
        this.email = email;
        this.phone = phone;
        this.ssn = ssn;
        this.address = address;
        this.date = date;
    }

    public EmployeeUpdate() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
