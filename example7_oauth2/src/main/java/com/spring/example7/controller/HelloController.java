package com.spring.example7.controller;

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

    // Сюда доступ разрешён только для авторизованных юзеров
    @GetMapping("/user")
    public String user() {
        log.debug("");
        log.debug("HelloController -> method user()");
        return "User";
    }
}
