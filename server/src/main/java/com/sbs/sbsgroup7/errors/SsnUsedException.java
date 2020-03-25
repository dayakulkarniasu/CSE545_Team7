package com.sbs.sbsgroup7.errors;

public class SsnUsedException extends RuntimeException {
    private static final long serialVersionUID = -1L;

    public SsnUsedException() {
        super("SSN is already used!");
    }
}