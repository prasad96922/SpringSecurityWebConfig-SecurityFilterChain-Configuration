package com.Lvprasad.SecurityAppApplication.SecurityApp.services;


import com.Lvprasad.SecurityAppApplication.SecurityApp.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

@Service
//1. create the JWT Token;
//2. Verify the JWT Token;
public class JwtService {

    @Value("${jwt.secretkey}")
    private String jwtSecretKey;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor((jwtSecretKey.getBytes(StandardCharsets.UTF_8)));

//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        return Keys.hmacShaKeyFor(keyBytes);
    };


    public String generateToken(UserEntity user) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("roles", Set.of("ADMIN", "USER"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60))
                .signWith(getSecretKey())
                .compact();
    }

    public  Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
         return Long.valueOf(claims.getSubject());
//        return Long.parseLong(claims.get("id").toString());

    }
}
