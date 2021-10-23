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

@Service
@Log4j2
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.sessionTime}")
    private long sessionTime;

    // Генерация токена (кладём в него имя пользователя и authorities)
    public String generateToken(UserDetails userDetails) {
        log.debug("Генерация токена");
        log.debug("  userDetails: " + userDetails);

        String commaSeparatedListOfAuthorities = userDetails.getAuthorities()
                .stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.joining(","));
        log.debug("  commaSeparatedListOfAuthorities: " + commaSeparatedListOfAuthorities);

        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", commaSeparatedListOfAuthorities);
        log.debug("  claims: " + claims);

        String token = createToken(claims, userDetails.getUsername());
        log.debug("  token: " + token);

        return token;
    }

    // Извлечение имени пользователя из токена (внутри валидация токена)
    public String extractUsername(String token) {
        log.debug("Извлечение имени пользователя из токена");
        log.debug("  token: " + token);

        String s = extractClaim(token, Claims::getSubject);
        log.debug("  s: " + s);

        return s;
    }

    // Извлечение authorities (внутри валидация токена)
    public String extractAuthorities(String token) {
        log.debug("Извлечение authorities");
        log.debug("  token: " + token);

        Function<Claims, String> claimsListFunction = claims -> (String) claims.get("authorities");
        log.debug("  claimsListFunction: " + claimsListFunction);

        String s = extractClaim(token, claimsListFunction);
        log.debug("  s: " + s);

        return s;
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        log.debug("Method extractClaim()");
        log.debug("  token: " + token);
        log.debug("  claimsResolver: " + claimsResolver);

        final Claims claims = extractAllClaims(token);
        log.debug("  claims: " + claims);

        T apply = claimsResolver.apply(claims);
        log.debug("  apply: " + apply);

        return apply;
    }

    private Claims extractAllClaims(String token) {
        log.debug("Method extractAllClaims()");
        log.debug("  token: " + token);

        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        log.debug("  claims: " + claims);
        return claims;
    }

    private String createToken(Map<String, Object> claims, String subject) {
        log.debug("Method createToken()");
        log.debug("  claims: " + claims);
        log.debug("  subject: " + subject);

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expireTimeFromNow())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        log.debug("  token: " + token);
        return token;
    }

    private Date expireTimeFromNow() {
        log.debug("Method expireTimeFromNow()");

        Date date = new Date(System.currentTimeMillis() + sessionTime);
        log.debug("  date: " + date);

        return date;
    }
}
