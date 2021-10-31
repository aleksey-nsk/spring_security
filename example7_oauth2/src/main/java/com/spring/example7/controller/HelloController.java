package com.spring.example7.controller;

import com.spring.example7.model.MyUser;
import com.spring.example7.repository.MyUserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
public class HelloController {

    @Autowired
    private MyUserRepository repo;

    // Есть доступ у неавторизованных юзеров
    @GetMapping("/")
    public String hello() {
        log.debug("");
        log.debug("HelloController -> method hello()");
        return "Hello";
    }

    // Есть доступ только у авторизованных юзеров
    @GetMapping("/user")
    public String user() {
        log.debug("");
        log.debug("HelloController -> method user()");
        return "User";
    }

    // Есть доступ у неавторизованных юзеров.
    // Добавил данную точку, чтобы смотреть список юзеров в БД
    @GetMapping("/all")
    public String all() {
        log.debug("");
        log.debug("HelloController -> method all()");

        List<MyUser> all = repo.findAll();
        log.debug("  все юзеры в БД: " + all);

        return all.toString();
    }
}
