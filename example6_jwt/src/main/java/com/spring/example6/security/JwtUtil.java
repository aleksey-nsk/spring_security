package com.spring.example6.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

// Для работы с JWT-токеном используем библиотеку JJWT (Java JWT: JSON Web Token for Java and Android).
// Во всех методах извлечения данных JWT-токен заодно проверяется на предмет, не истёк ли он, и валидна ли подпись.

@Service
@Log4j2
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.sessionTime}")
    private long sessionTime;

    // Генерация токена (кладём в него имя пользователя и authorities)
    public String generateToken(UserDetails userDetails) {
        log.debug("");
        log.debug("  Генерация токена");
        log.debug("    userDetails: " + userDetails);

        String commaSeparatedListOfAuthorities = userDetails.getAuthorities()
                .stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.joining(","));
        log.debug("    commaSeparatedListOfAuthorities: " + commaSeparatedListOfAuthorities);

        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", commaSeparatedListOfAuthorities);
        log.debug("    claims: " + claims);

        String token = createToken(claims, userDetails.getUsername());
        log.debug("    token: " + token);
        log.debug("");
        return token;
    }

    private String createToken(Map<String, Object> claims, String subject) {
        log.debug("    Method createToken()");
        log.debug("      claims: " + claims);
        log.debug("      subject: " + subject);

        // Генерируем токен с помощью библиотеки JJWT
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expireTimeFromNow())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return token;
    }

    private Date expireTimeFromNow() {
        log.debug("    Method expireTimeFromNow()");

        Date date = new Date(System.currentTimeMillis() + sessionTime);
        log.debug("      sessionTime: " + sessionTime);
        log.debug("      date: " + date);

        return date;
    }

    // Извлечение имени пользователя из токена (внутри валидация токена)
    public String extractUsername(String token) {
        log.debug("");
        log.debug("  Извлечение имени пользователя из токена");
        log.debug("    token: " + token);

        String username = extractClaim(token, Claims::getSubject);
        log.debug("    username: " + username);
        log.debug("");

        return username;
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        log.debug("    Method extractClaim()");
        log.debug("      token: " + token);

        final Claims claims = extractAllClaims(token);
        log.debug("      claims: " + claims);

        T apply = claimsResolver.apply(claims);
        log.debug("      apply: " + apply);

        return apply;
    }

    private Claims extractAllClaims(String token) {
        log.debug("      Method extractAllClaims()");
        log.debug("        token: " + token);
        log.debug("        secretKey: " + secretKey);

        // Используем библиотеку JJWT
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        log.debug("        claims: " + claims);
        return claims;
    }

    // Извлечение authorities (внутри валидация токена)
    public String extractAuthorities(String token) {
        log.debug("");
        log.debug("  Извлечение authorities");
        log.debug("    token: " + token);

        Function<Claims, String> claimsListFunction = claims -> (String) claims.get("authorities");

        String authorities = extractClaim(token, claimsListFunction);
        log.debug("    authorities: " + authorities);
        log.debug("");

        return authorities;
    }
}
