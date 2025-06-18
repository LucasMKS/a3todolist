package com.a3.todo.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import com.a3.todo.entity.Usuario;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
@Service
public class JwtService {

    private static final String secret = "4ee383d2a36c1e6ac7b7ba0d6e0c6dd1315ad630908bb2ce265e5e72ad8188b134f5759a1dadfbde49e503dc6cb8b47ed26d234882f8b86a479663f86516981b4f99d7d91d3d9c3d84f2071f285a345e0499f5447cd62e8bbc574db31b7a47d80e6738cfec5c55e957c8506e63cf90f06bee34fe00892b7b7d69f6ab6844c7";

  private Key getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Usuario user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(
                    LocalDateTime.now()
                                 .plusDays(1)
                                 .atZone(ZoneId.systemDefault())
                                 .toInstant()))
                // Aqui passamos a Key e o algoritmo
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(getSigningKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody()
                   .getSubject();
    }

    public boolean validateToken(String token, Usuario user) {
        return extractEmail(token).equals(user.getEmail());
    }
}