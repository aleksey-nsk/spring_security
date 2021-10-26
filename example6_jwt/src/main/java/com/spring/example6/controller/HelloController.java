package com.spring.example6.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class HelloController {

    @GetMapping("/")
    public String hello() {
        log.debug("");
        log.debug("HelloController -> method hello()");
        return "Hello";
    }

    // Сюда доступ разрешен только для USER и ADMIN
    @GetMapping("/user")
    public String user() {
        log.debug("");
        log.debug("HelloController -> method user()");
        return "User";
    }

    // Сюда доступ разрешен только для ADMIN
    @GetMapping("/admin")
    public String admin() {
        log.debug("");
        log.debug("HelloController -> method admin()");
        return "Admin";
    }
}
