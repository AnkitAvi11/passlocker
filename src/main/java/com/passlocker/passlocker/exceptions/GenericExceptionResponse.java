package com.passlocker.passlocker.exceptions;

public class GenericExceptionResponse {
    private String message;
    private String status;
    private String path;
    private String timeStamp;

    public GenericExceptionResponse() {}

    public GenericExceptionResponse(String message, String status, String path, String timeStamp) {
        this.message = message;
        this.status = status;
        this.path = path;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public GenericExceptionResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public GenericExceptionResponse setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getPath() {
        return path;
    }

    public GenericExceptionResponse setPath(String path) {
        this.path = path;
        return this;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public GenericExceptionResponse setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }
}
