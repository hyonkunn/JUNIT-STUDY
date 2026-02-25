package com.junit.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.junit.test.repository.PasswordEncoder;

@Configuration
public class SecurityConfig {
    

    
@Bean
public com.junit.test.repository.PasswordEncoder passwordEncoder() {
    org.springframework.security.crypto.password.PasswordEncoder springEncoder = new BCryptPasswordEncoder();
    
    return new com.junit.test.repository.PasswordEncoder() {
        @Override
        public String encode(String rawPassword) {
            return springEncoder.encode(rawPassword);
        }
    };
}
}

