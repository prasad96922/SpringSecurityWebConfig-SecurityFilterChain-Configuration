package com.Lvprasad.SecurityAppApplication.SecurityApp.services;


import com.Lvprasad.SecurityAppApplication.SecurityApp.entities.UserEntity;
import com.Lvprasad.SecurityAppApplication.SecurityApp.exceptions.JwtAuthenticationException;
import io.jsonwebtoken.*;
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


    public String
    generateAccessToken(UserEntity user) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("roles", Set.of("ADMIN", "USER"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*10))
                .signWith(getSecretKey())
                .compact();
    }

    public String generateRefreshToken(UserEntity user) {
        return Jwts.builder()
                .subject(user.getId().toString())
//                .claim("email", user.getEmail())
//                .claim("roles", Set.of("ADMIN", "USER"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*24*30))
                .signWith(getSecretKey())
                .compact();
    }

    public Long getUserIdFromToken(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.valueOf(claims.getSubject());

//        try {
//
//            Claims claims = Jwts.parser()
//                    .verifyWith(getSecretKey())
//                    .build()
//                    .parseSignedClaims(token)
//                    .getPayload();
//
//            return Long.valueOf(claims.getSubject());
//
//        } catch (ExpiredJwtException ex) {
//            throw new JwtAuthenticationException("Token Expired");
//        } catch (MalformedJwtException ex) {
//            throw new JwtAuthenticationException("Invalid Token");
//        }
    }
}
