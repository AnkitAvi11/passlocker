package com.passlocker.passlocker.exceptions;

public class BadPermissionException extends RuntimeException {
    private String message;

    public BadPermissionException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public BadPermissionException setMessage(String message) {
        this.message = message;
        return this;
    }
}
