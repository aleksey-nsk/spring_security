package com.spring.example4.config;

import com.spring.example4.model.MyUser;
import com.spring.example4.repository.MyUserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class CustomAuthencationProvider implements AuthenticationProvider {

    @Autowired
    private MyUserRepository repository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        List<MyUser> all = repository.findAll();
        log.debug("all users in DB: " + all);

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        log.debug("username: " + username);
        log.debug("password: " + password);

        MyUser myUser = repository.findByUsername(username);
        log.debug("myUser: " + myUser);

        if (myUser == null) {
            log.error("Unknown user: " + username);
            throw new BadCredentialsException("Unknown user: " + username);
        }

        if (!password.equals(myUser.getPassword())) {
            log.error("Bad password: " + password);
            throw new BadCredentialsException("Bad password: " + password);
        }

        UserDetails userDetails = User.builder()
                .username(myUser.getUsername())
                .password(myUser.getPassword())
                .roles(myUser.getRole())
                .build();

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
