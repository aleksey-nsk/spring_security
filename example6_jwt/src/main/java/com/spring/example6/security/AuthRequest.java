package com.spring.example6.security;

import lombok.Data;

@Data
public class AuthRequest { // формат запроса

    private String name;
    private String password;
}
