package com.Lvprasad.SecurityAppApplication.SecurityApp.exceptions;


import io.jsonwebtoken.JwtException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class JwtAuthenticationException extends RuntimeException {


    public JwtAuthenticationException(String message) {
        super(message);
    }
}
