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

/**
 * <p>Фильтр, проверяющий JWT-токен при каждом запросе</p>
 *
 * <p>После того как JWT-токен выдан, клиент его отправляет при каждом запросе. Надо этот токен
 * при каждом запросе проверять (и извлекать из него имя пользователя). Для этого используем
 * фильтр JwtFilter (он расширяет OncePerRequestFilter)</p>
 */
@Component
@Log4j2
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.debug("Method doFilterInternal()");
        log.debug("  request: " + request);
        log.debug("  response: " + response);
        log.debug("  chain: " + chain);

        final String authorizationHeader = request.getHeader("Authorization");
        log.debug("  authorizationHeader: " + authorizationHeader);

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            log.debug("  jwt: " + jwt);

            // Если подпись не совпадает с вычисленной => то SignatureException
            // Если подпись некорректная (не парсится) => то MalformedJwtException
            // Если подпись истекла по времени => то ExpiredJwtException

            username = jwtUtil.extractUsername(jwt);
            log.debug("  username: " + username);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            String commaSeparatedListOfAuthorities = jwtUtil.extractAuthorities(jwt);
            log.debug("  commaSeparatedListOfAuthorities: " + commaSeparatedListOfAuthorities);

            List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(commaSeparatedListOfAuthorities);
            log.debug("  authorities: " + authorities);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            log.debug("  authenticationToken: " + authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        chain.doFilter(request, response);
    }
}
