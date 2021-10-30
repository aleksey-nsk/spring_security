package com.spring.example6.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

// JwtFilter - это фильтр, проверяющий JWT-токен при каждом запросе.
//
// После того, как JWT-токен выдан, клиент его отправляет при каждом запросе.
// И проверять этот токен надо при каждом запросе (и извлекать из него имя пользователя).
// Для этого используем фильтр JwtFilter (он расширяет OncePerRequestFilter).

@Component
@Log4j2
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.debug("");
        log.debug("Method doFilterInternal()");

        final String authorizationHeader = request.getHeader("Authorization");
        log.debug("  authorizationHeader: " + authorizationHeader);

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // из заголовка Authorization берём JWT-токен
            log.debug("  jwt: " + jwt);

            username = jwtUtil.extractUsername(jwt); // извлекаем из JWT-токена имя пользователя
            log.debug("  username: " + username);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            String commaSeparatedListOfAuthorities = jwtUtil.extractAuthorities(jwt); // извлекаем из JWT-токена список authorities
            log.debug("  commaSeparatedListOfAuthorities: " + commaSeparatedListOfAuthorities);

            // Одновременно при извлечении claims (имя пользователя и список authorities) проверяется валидность токена.
            // Для этого не надо делать никаких запросов в базу: достаточно самого токена и jwt.secret (прописанного в application.yml).
            // На основе этого секрета токен генерился, и на основе него он потом каждый раз проверяется
            // с помощью хеш-функции (это делает библиотека JJWT).

            List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(commaSeparatedListOfAuthorities);
            log.debug("  authorities: " + authorities);

            // Если всё ок, то имея имя пользователя и список authorities, создаём объект Authentication
            // (точнее, его подкласс UsernamePasswordAuthenticationToken).
            // И устанавливаем объект Authentication в SecurityContext. Так нужно для Spring Security.
            //
            // Если с токеном не всё ок, то выбросится исключение, и фильтр не пропустит запрос
            // в контроллер к защищённому урлу.

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            log.debug("  authenticationToken: " + authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        chain.doFilter(request, response);
    }
}
