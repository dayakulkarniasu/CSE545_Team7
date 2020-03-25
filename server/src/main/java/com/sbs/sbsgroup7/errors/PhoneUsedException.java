package com.sbs.sbsgroup7.errors;

public class PhoneUsedException extends RuntimeException {
    private static final long serialVersionUID = -1L;

    public PhoneUsedException() {
        super("Phone Number is already used!");
    }
}
