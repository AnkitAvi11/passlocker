package com.passlocker.passlocker.controllers.requests;

public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String username;

    public UserUpdateRequest() {}

    public UserUpdateRequest(String firstName, String lastName, String emailAddress, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserUpdateRequest setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserUpdateRequest setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public UserUpdateRequest setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserUpdateRequest setUsername(String username) {
        this.username = username;
        return this;
    }
}
