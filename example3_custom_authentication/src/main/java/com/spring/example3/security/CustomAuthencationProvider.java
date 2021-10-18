//package com.spring.example3.security;
//
//import com.spring.example3.model.MyUser;
//import com.spring.example3.repository.MyUserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CustomAuthencationProvider implements AuthenticationProvider {
//
//    @Autowired
//    private MyUserRepository repository;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//        String password = authentication.getCredentials().toString();
//
//        MyUser myUser = repository.findByUsername(username);
//        if (myUser == null) {
//            throw new BadCredentialsException("Unknown user: " + username);
//        }
//        if (!password.equals(myUser.getPassword())) {
//            throw new BadCredentialsException("Bad password");
//        }
//
//        UserDetails userDetails = User.builder()
//                .username(myUser.getUsername())
//                .password(myUser.getPassword())
//                .roles(myUser.getRole())
//                .build();
//
//        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
//    }
//}
