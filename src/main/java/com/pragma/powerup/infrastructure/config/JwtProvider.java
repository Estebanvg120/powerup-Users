package com.pragma.powerup.infrastructure.config;

import com.pragma.powerup.domain.model.UserModel;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtProvider {
    private Key key;
    private final long expiration;

    public JwtProvider(@Value("${jwt.secret}") String base64Secret, @Value("${jwt.expiration}") long expiration) {
        byte[] keyBytes = Base64.getDecoder().decode(base64Secret);
        this.expiration = expiration;
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserModel userModel) {
        return Jwts.builder()
                .setSubject(userModel.getEmail())
                .claim("role", userModel.getRole())
                .claim("username", userModel.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
    //Email
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().get("username", String.class);
    }

    // Rol
    public String getRoleFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().get("role", String.class);
    }

    // Roles
    public List<SimpleGrantedAuthority> getAuthoritiesFromToken(String token) {
        String role = getRoleFromToken(token);
        return role != null ? List.of(new SimpleGrantedAuthority(role)) : List.of();
    }
}
