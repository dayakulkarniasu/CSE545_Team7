package com.sbs.sbsgroup7.errors;

public class EmailUsedException extends RuntimeException {
    private static final long serialVersionUID = -1L;

    public EmailUsedException() {
        super("Email is already used!");
    }
}
