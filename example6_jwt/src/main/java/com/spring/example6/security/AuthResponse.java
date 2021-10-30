package com.spring.example6.security;

import lombok.Data;

// Формат ответа
@Data
public class AuthResponse {

    private String jwtToken;

    public AuthResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
