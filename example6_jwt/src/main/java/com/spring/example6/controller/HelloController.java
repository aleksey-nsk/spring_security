package com.spring.example6.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Это наш основной REST-контроллер.
// Он нужен для того, чтобы запретить к нему доступ, и потом разрешить только авторизованным пользователям.

@RestController
@Log4j2
public class HelloController {

    @GetMapping("/")
    public String hello() {
        log.debug("");
        log.debug("HelloController -> method hello()");
        return "Hello";
    }

    // Сюда доступ разрешён только для USER и ADMIN
    @GetMapping("/user")
    public String user() {
        log.debug("");
        log.debug("HelloController -> method user()");
        return "User";
    }

    // Сюда доступ разрешён только для ADMIN
    @GetMapping("/admin")
    public String admin() {
        log.debug("");
        log.debug("HelloController -> method admin()");
        return "Admin";
    }
}
