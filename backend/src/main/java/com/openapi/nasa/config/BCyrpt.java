package com.openapi.nasa.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCyrpt {
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }
}
