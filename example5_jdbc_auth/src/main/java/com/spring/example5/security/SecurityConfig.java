package com.spring.example5.security;

//import com.spring.example5.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
//@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private CustomUserDetailsService service;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // Настройка аутентификации
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(service);

        //Для JDBC-аутентификация в Spring Security предусмотрен метод auth.jdbcAuthentication()
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .withDefaultSchema()
                .withUser(User.withUsername("user").password("pswd1").roles("USER"))
                .withUser(User.withUsername("admin").password("pswd2").roles("ADMIN"));

        // .dataSource(dataSource) - источник данных задаётся этим методом,
        // причём бин dataSource создавать не нужно, достаточно просто внедрить. Spring Boot его создает за нас.

        // .withDefaultSchema() - означает, что схема, где хранятся пользователи, создается автоматически,
        // и в нее добавляются пользователи, указанные далее. В документации есть эта схема, в ней
        // 2 таблицы — пользователей и прав. Вышеприведенный фрагмент кода можно трактовать так: Spring Securuty,
        // создай нам в базе H2 свои стандартные таблицы и добавь туда 2 пользователей с такими-то именами, паролями и правами.
    }

    // Настройка авторизации
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/**").permitAll()
                .and().formLogin();
    }
}
