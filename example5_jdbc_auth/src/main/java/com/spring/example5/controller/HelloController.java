package com.spring.example5.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Log4j2
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Hello";
    }

    @GetMapping("/user")
    public String user() {
//    public String user(Authentication authentication) {
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        log.debug("userDetails: " + userDetails);
        return "User";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Admin";
    }
}
