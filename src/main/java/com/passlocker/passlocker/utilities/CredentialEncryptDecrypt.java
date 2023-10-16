package com.passlocker.passlocker.utilities;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "credential_encryptor_decryptor")
public class CredentialEncryptDecrypt {

    private StandardPBEStringEncryptor encryptor;

    @Value("${SECRET_KEY}")
    private String SECRET_KEY;

    @Autowired
    public void setEncryptor(StandardPBEStringEncryptor encryptor) {
        this.encryptor = encryptor;
        this.encryptor.setPassword(SECRET_KEY);
    }

    public String encrypt(String credential) throws Exception {
        this.encryptor.setPassword(SECRET_KEY);
        return encryptor.encrypt(credential);
    }

    public String decrypt(String encryptedCredential) throws Exception {
        return this.encryptor
                .decrypt(encryptedCredential);
    }
}
