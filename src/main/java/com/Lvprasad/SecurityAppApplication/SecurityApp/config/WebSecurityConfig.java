package com.Lvprasad.SecurityAppApplication.SecurityApp.config;


import com.Lvprasad.SecurityAppApplication.SecurityApp.filters.JwtRequestAuthFilter;
import com.Lvprasad.SecurityAppApplication.SecurityApp.handlers.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.Lvprasad.SecurityAppApplication.SecurityApp.entities.enums.Permission.*;
import static com.Lvprasad.SecurityAppApplication.SecurityApp.entities.enums.Role.ADMIN;
import static com.Lvprasad.SecurityAppApplication.SecurityApp.entities.enums.Role.CREATOR;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtRequestAuthFilter jwtRequestAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private static final  String[] publicRoutes = {"/error","/auth/**", "/login", "/home.html",};

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtRequestAuthFilter jwtRequestAuthFilter) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth.
                        requestMatchers(publicRoutes).permitAll()
                        .requestMatchers(HttpMethod.GET, "/post/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/posts/**")
                        .hasAnyRole(ADMIN.name(), CREATOR.name())
                        .requestMatchers(HttpMethod.POST, "/posts/**")
                        .hasAnyAuthority(POST_CREATE.name())
                        .requestMatchers(HttpMethod.GET, "/posts/**")
                        .hasAuthority(POST_VIEW.name())
                        .requestMatchers(HttpMethod.PUT, "/posts/**").hasAuthority(POST_UPDATE.name())
                        .requestMatchers(HttpMethod.DELETE, "/posts/**").hasAuthority(POST_DELETE.name())
                        .anyRequest().authenticated())

                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        sessionManagementConfig->sessionManagementConfig
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2Config-> oauth2Config
                        .failureUrl("/login?error=true")
                        .successHandler(oAuth2SuccessHandler)
                );
        return httpSecurity.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();

    }

//    @Bean
//    UserDetailsService myInMemoryUserDetailsService() {
//        UserDetails user = User
//                .withUsername("lvprasad")
//                .password(passwordEncoder().encode("lvp123"))
//                .roles("USER").build();
//
//        UserDetails adminUser = User
//                .withUsername("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(user, adminUser);
//
//    }


}
