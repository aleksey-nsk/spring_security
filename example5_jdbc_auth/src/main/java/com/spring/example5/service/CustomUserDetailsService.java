package com.spring.example5.service;

import com.spring.example5.model.MyUser;
import com.spring.example5.repository.MyUserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MyUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<MyUser> all = repository.findAll();
        log.debug("all users in DB: " + all);

        MyUser myUser = repository.findByUsername(username);
        log.debug("myUser: " + myUser);

        if (myUser == null) {
            throw new UsernameNotFoundException("Unknown user: " + username);
        }

        UserDetails userDetails = User.builder()
                .username(myUser.getUsername())
                .password(myUser.getPassword())
                .roles(myUser.getRole())
                .build();

        return userDetails;
    }
}
