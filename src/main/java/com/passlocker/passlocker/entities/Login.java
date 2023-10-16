package com.passlocker.passlocker.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "logins")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String loginId;

    private String loginName;
    private String username;
    private String password;
}
