package com.authenticationservice.server.utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final String SECRET_KEY = "your-secret-key";
    private static final long VALIDITY_DURATION_MS = 3600000; // 1 hour

    public String generateToken(String username) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + VALIDITY_DURATION_MS);

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", "USER")
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        String username = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
        return new UsernamePasswordAuthenticationToken(username, "", new ArrayList<>());
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}