package com.passlocker.passlocker.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.passlocker.passlocker.entities.enums.UserType;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "GET_USERS", query = "select users from UserEntity users order by updatedAt desc "),
        @NamedQuery(name = "COUNT_USERS", query = "select count (users) from UserEntity users")
})
@JsonPropertyOrder({"userId", "username", "firstName", "lastName", "emailAddress", "userType", "active", "blocked", "createdAt", "updatedAt"})
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private String userId;

    @Column(name = "username", columnDefinition = "CHAR(36)", nullable = false, unique = true)
    private String username;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = true)
    private String lastName;
    @Column(name = "email_address", nullable = false, unique = true)
    private String emailAddress;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @Column(name = "blocked", nullable = false)
    private boolean blocked = false;

    @Enumerated(EnumType.STRING)    //  determines the role of the user (staff, admin, user)
    private UserType userType;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public UserEntity() {}

    public UserEntity(String userId, String username, String firstName, String lastName, String emailAddress, String password, boolean active, boolean blocked, UserType userType, Date createdAt, Date updatedAt) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.active = active;
        this.blocked = blocked;
        this.userType = userType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getUserId() {
        return userId;
    }

    public UserEntity setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public UserEntity setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public UserEntity setActive(boolean active) {
        this.active = active;
        return this;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public UserEntity setBlocked(boolean blocked) {
        this.blocked = blocked;
        return this;
    }

    public UserType getUserType() {
        return userType;
    }

    public UserEntity setUserType(UserType userType) {
        this.userType = userType;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public UserEntity setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public UserEntity setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
