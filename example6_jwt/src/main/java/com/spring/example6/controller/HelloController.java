package com.spring.example6.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Hello";
    }

    // Сюда доступ разрешен только USER и ADMIN
    @GetMapping("/user")
    public String user() {
        return "User";
    }

    // Сюда доступ разрешен только ADMIN
    @GetMapping("/admin")
    public String admin() {
        return "Admin";
    }
}
