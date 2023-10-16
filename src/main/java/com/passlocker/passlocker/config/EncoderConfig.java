package com.passlocker.passlocker.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncoderConfig {

    @Bean
    public StandardPBEStringEncryptor standardPBEStringEncryptor() {
        return new StandardPBEStringEncryptor();
    }
}
