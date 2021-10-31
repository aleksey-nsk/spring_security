package com.spring.example7.config;

import com.spring.example7.model.MyUser;
import com.spring.example7.repository.MyUserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso // для OAuth 2 нужна
@Log4j2
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .antMatcher("/**")
//                .authorizeRequests()
//                .antMatchers("/", "/login**", "/js/**", "/error**").permitAll()
//                .anyRequest().authenticated()
//                .and().logout().logoutSuccessUrl("/").permitAll()
//                .and()
//                .csrf().disable();

        http.authorizeRequests()
                .mvcMatchers("/").permitAll() // позволим заходить на аппликейшен неавторизованным людям
                .anyRequest().authenticated()
                .and()
                .csrf().disable(); // отключим csrf
    }

    // Добавим сохранение авторизованного пользователя в нашу БД
    @Bean
//    public PrincipalExtractor principalExtractor(UserDetailsRepo userDetailsRepo) {
    public PrincipalExtractor principalExtractor(MyUserRepository repo) {
//        log.debug("");
//        log.debug("Method principalExtractor()");
//        log.debug("  repo: " + repo);

        return map -> {
            log.debug("");
            log.debug("Method principalExtractor()");
            log.debug("  repo: " + repo);

            String id = (String) map.get("sub"); // это поле содержит айдишник
            log.debug("  id: " + id);

            // Пользователя по айдишнику ищем в БД.
            // И если не находим то создаём нового пользователя
            MyUser user = repo.findById(id).orElseGet(() -> {
                MyUser newUser = new MyUser();

                newUser.setId(id);
                newUser.setUsername((String) map.get("name"));
                newUser.setEmail((String) map.get("email"));
                newUser.setGender((String) map.get("gender"));
                newUser.setLocale((String) map.get("locale"));
                newUser.setUserpic((String) map.get("picture"));

                log.debug("  newUser: " + newUser);
                return newUser;
            });

//            user.setLastVisit(LocalDateTime.now()); // ставим польз-лю ласт_визит

            log.debug("  user: " + user);

            return repo.save(user);

//            String id = map.get("sub");// это поле содержит айдишник
//            return new User();
        };
    }
}
