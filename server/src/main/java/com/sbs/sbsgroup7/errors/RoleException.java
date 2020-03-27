package com.sbs.sbsgroup7.errors;

public class RoleException extends RuntimeException {
    private static final long serialVersionUID = -1L;

    public RoleException() {
        super("Role is invalid!");
    }
}
