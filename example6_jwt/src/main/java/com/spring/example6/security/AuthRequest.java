package com.spring.example6.security;

import lombok.Data;

// Формат запроса
@Data
public class AuthRequest {

    private String name;
    private String password;
}
