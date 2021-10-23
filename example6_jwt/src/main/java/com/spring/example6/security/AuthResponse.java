package com.spring.example6.security;

import lombok.Data;

@Data
public class AuthResponse { // формат ответа

    private String jwtToken;

    public AuthResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
