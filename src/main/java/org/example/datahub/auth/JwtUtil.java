package org.example.datahub.auth;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String JWT_SECRET = "DataHubSecretKeyAttendToBeAtLeast32CharactersLong";
    private static final SecretKey JWT_SECRET_KEY = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
    private static final Long JWT_EXPIRATION_MS = 3600000L; // 1 hour

    public String generateToken(Long userId) {
        return Jwts.builder()
            .setSubject(String.valueOf(userId))
            .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS))
            .signWith(JWT_SECRET_KEY)
            .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(JWT_SECRET_KEY)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public Long getCurrentUserId(String token) {
        try {
            Claims claims = parseToken(token);
            return Long.parseLong(claims.getSubject());
        } catch (Exception e) {
            return null;
        }
    }
}
