package com.junit.test.repository;

import org.springframework.stereotype.Component;

@Component 
public class PasswordEncoder {
    public String encode(String password) {
        return "encoded_" + password;
    }
}