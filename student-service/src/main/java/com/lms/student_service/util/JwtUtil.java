package com.lms.student_service.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey key;
    private final int expirationMinutes;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.secret.base64:false}") boolean base64,
            @Value("${jwt.expiration.minutes:60}") int expirationMinutes
    ) {
        // Support raw or Base64 secrets (>= 32 bytes for HS256)
        if (base64) {
            this.key = Keys.hmacShaKeyFor(io.jsonwebtoken.io.Decoders.BASE64.decode(secret));
        } else {
            this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        }
        this.expirationMinutes = expirationMinutes;
    }

    public String generateToken(String username) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(expirationMinutes * 60L);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return parse(token).getBody().getSubject();
    }

    public boolean validateToken(String token, org.springframework.security.core.userdetails.UserDetails userDetails) {
        try {
            var claims = parse(token).getBody();
            return userDetails.getUsername().equals(claims.getSubject())
                    && claims.getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isTokenValid(String token) {
        try {
            var claims = parse(token).getBody();
            return claims.getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Jws<Claims> parse(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }
}
