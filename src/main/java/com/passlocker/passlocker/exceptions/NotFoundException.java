package com.passlocker.passlocker.exceptions;

public class NotFoundException extends RuntimeException {
    private String message;

    public NotFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public NotFoundException setMessage(String message) {
        this.message = message;
        return this;
    }
}
