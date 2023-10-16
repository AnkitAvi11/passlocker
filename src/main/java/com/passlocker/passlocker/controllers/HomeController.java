package com.passlocker.passlocker.controllers;

import com.passlocker.passlocker.utilities.CredentialEncryptDecrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class HomeController {

    @Qualifier("credential_encryptor_decryptor")
    @Autowired
    private CredentialEncryptDecrypt credentialEncryptDecrypt;

    @GetMapping("")
    public String getHome() {
        return "Passlocker - API";
    }
}
