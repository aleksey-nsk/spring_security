package com.spring.example3.service;

import com.spring.example3.model.MyUser;
import com.spring.example3.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MyUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username: " + username);

        MyUser myUser = repository.findByUsername(username);
        System.out.println("myUser: " + myUser);

        List<MyUser> all = repository.findAll();
        System.out.println("all: " + all);

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
