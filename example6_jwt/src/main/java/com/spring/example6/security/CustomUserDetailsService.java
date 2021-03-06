package com.spring.example6.security;

import com.spring.example6.model.MyUser;
import com.spring.example6.repository.MyUserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

// Аутентификация с пользовательским UserDetailsService.
//
// Мы переопределяем метод loadUserByUsername(), чтобы Spring Security понимал,
// как взять пользователя по его имени из хранилища.
// Имея этот метод, Spring Security может сравнить переданный пароль с настоящим
// и аутентифицировать пользователя (либо не аутентифицировать).

@Service
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MyUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("  Method loadUserByUsername()");
        log.debug("    username: " + username);

        List<MyUser> all = repository.findAll();
        log.debug("    all users in DB: " + all);

        MyUser myUser = repository.findByUsername(username);
        log.debug("    myUser: " + myUser);

        if (myUser == null) {
            log.error("    Unknown user: " + username);
            throw new UsernameNotFoundException("Unknown user: " + username);
        }

        UserDetails userDetails = User.builder()
                .username(myUser.getUsername())
                .password(myUser.getPassword())
                .roles(myUser.getRole())
                .build();

        log.debug("    userDetails: " + userDetails);
        return userDetails;
    }
}
