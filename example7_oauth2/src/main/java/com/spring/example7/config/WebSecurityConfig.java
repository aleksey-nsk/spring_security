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

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso // нужна для OAuth2
@Log4j2
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/").permitAll() // есть доступ у неавторизованных юзеров
                .mvcMatchers("/all").permitAll() // есть доступ у неавторизованных юзеров
                .anyRequest().authenticated()
                .and()
                .csrf().disable(); // отключим csrf
    }

    // Добавим сохранение авторизованного пользователя в нашу БД
    @Bean
    public PrincipalExtractor principalExtractor(MyUserRepository repo) {
        return map -> {
            log.debug("");
            log.debug("Method principalExtractor()");

            String id = (String) map.get("sub");
            log.debug("  от Google пришёл id: " + id);

            // Ищем юзера в БД по id,
            // и если не находим то создаём нового
            MyUser user = repo.findById(id).orElseGet(() -> {
                log.debug("  не нашли в БД юзера с id: " + id);

                MyUser newUser = new MyUser();
                newUser.setId(id);
                newUser.setUsername((String) map.get("name"));
                newUser.setEmail((String) map.get("email"));
                newUser.setGender((String) map.get("gender"));
                newUser.setLocale((String) map.get("locale"));
                newUser.setUserpic((String) map.get("picture"));
                log.debug("  создали нового юзера newUser: " + newUser);

                return newUser;
            });

            log.debug("  user: " + user);
            return repo.save(user);
        };
    }
}
