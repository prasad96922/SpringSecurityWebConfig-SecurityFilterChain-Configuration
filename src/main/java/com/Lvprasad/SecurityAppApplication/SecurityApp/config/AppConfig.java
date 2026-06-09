package com.Lvprasad.SecurityAppApplication.SecurityApp.config;



import com.Lvprasad.SecurityAppApplication.SecurityApp.exceptions.JwtAuthenticationException;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//@EnableJpaAuditing(auditorAwareRef = "getAuditorAwareImpl")
public class AppConfig {

    @Bean
    ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    JwtAuthenticationException getJwtAuthenticationException() {
//        return new JwtAuthenticationException("Jwt Authentication Failed");
//    }


}
