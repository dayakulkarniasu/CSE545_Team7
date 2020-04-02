package com.sbs.sbsgroup7.model;

import javax.validation.constraints.*;

public class EmployeeInfo {

    @NotNull(message = "Required*")
    @NotEmpty(message = "Required*")
    @Email
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message="Email address is invalid")
    private String email;

    @NotNull(message = "Required*")
    @NotEmpty(message = "Required*")
    @Pattern(regexp="^[0-9][0-9]{2}-[0-9]{3}-[0-9]{4}$", message="Phone numbers must be in this format: 480-123-4567")
    private String phone;

    @NotNull(message = "Required*")
    @NotEmpty(message = "Required*")
    @Pattern(regexp = "^[0-9][0-9]{2}-[0-9]{2}-[0-9]{4}$", message="SSN must use numbers in this format: XXX-YY-ZZZZ")
    private String ssn;

    @NotNull(message = "Required*")
    @NotEmpty(message = "Required*")
    private String address;


    public String getEmail() { return email; }

    public String getPhone() { return phone; }

    public String getSsn() { return ssn; }

    public String getAddress()  { return address; }


    public void setEmail(String email) { this.email=email; }

    public void setPhone(String phone) { this.phone= phone; }

    public void setSsn(String ssn) { this.ssn=ssn; }

    public void setAddress(String address)  { this.address=address; }
}
