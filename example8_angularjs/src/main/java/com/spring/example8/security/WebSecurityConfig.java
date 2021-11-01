package com.spring.example8.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService users() {
        UserDetails userDetails = User.builder()
                .username("user")
                .password("{bcrypt}$2a$10$eWUSQWdNH9Akksdl1Ktote845SUUu9L1CyyP.RFPUaC3C11Iy6SRy") // пароль user
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated() // на любой запрос требуем аутентификацию
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // для AngularJS
                .and().formLogin(); // используем готовую форму логина от Спринга
    }
}
