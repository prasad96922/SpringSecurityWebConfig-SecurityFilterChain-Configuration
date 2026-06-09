package com.Lvprasad.SecurityAppApplication.SecurityApp.config;


import com.Lvprasad.SecurityAppApplication.SecurityApp.filters.JwtRequestAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtRequestAuthFilter jwtRequestAuthFilter;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtRequestAuthFilter jwtRequestAuthFilter) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth.
                        requestMatchers("/posts/**","/error","/auth/**", "/login").permitAll()
//                        .requestMatchers("/posts/**").hasRole("ADMIN")
//                        .requestMatchers("/posts/**").authenticated()
                        .anyRequest().authenticated())
                .csrf(csrfConfig->csrfConfig.disable())
                .sessionManagement(
                        sessionManagementConfig->sessionManagementConfig
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestAuthFilter, UsernamePasswordAuthenticationFilter.class);
//                .formLogin(Customizer.withDefaults());
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
