package com.spring.example6.controller;

import com.spring.example6.security.AuthRequest;
import com.spring.example6.security.AuthResponse;
import com.spring.example6.security.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

// Тут выдается JWT-токен. Пользователь делает POST-запрос с именем и паролем по адресу /authenticate,
// а в ответ получает сгенерированынй токен.
// Токен генерится методом generateToken() из утилитного класса JwtUtil

@RestController
@Log4j2
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        log.debug("Method createAuthenticationToken()");
        log.debug("  authRequest: " + authRequest);

        Authentication authentication;

        try {
            String name = authRequest.getName();
            String pswd = authRequest.getPassword();
            log.debug("  name: " + name);
            log.debug("  pswd: " + pswd);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(name, pswd);
            authentication = authenticationManager.authenticate(auth);
            log.debug("  authentication: " + authentication);

        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Имя или пароль неправильны", e);
        }

        // При создании токена в него кладётся username как Subject claim, и список authorities как кастомный claim
        String jwt = jwtUtil.generateToken((UserDetails) authentication.getPrincipal());
        log.debug("  jwt: " + jwt);

        AuthResponse authResponse = new AuthResponse(jwt);
        log.debug("  authResponse: " + authResponse);

        // Если имя и пароль верные, токен возвращается в AuthResponse, а если нет — выбрасывается исключение и на
        // фронтенд приходит сообщение об ошибке.
        // Фронтенд сохраняет у себя JWT-токен, и потом использует его при каждом запросе.

        return authResponse;
    }
}
