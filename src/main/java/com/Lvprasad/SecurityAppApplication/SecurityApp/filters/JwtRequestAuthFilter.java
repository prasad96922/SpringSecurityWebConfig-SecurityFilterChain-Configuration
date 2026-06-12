package com.Lvprasad.SecurityAppApplication.SecurityApp.filters;

import com.Lvprasad.SecurityAppApplication.SecurityApp.entities.UserEntity;
import com.Lvprasad.SecurityAppApplication.SecurityApp.exceptions.JwtAuthenticationException;
import com.Lvprasad.SecurityAppApplication.SecurityApp.services.JwtService;
import com.Lvprasad.SecurityAppApplication.SecurityApp.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    /* JWTException ---> Uses HttpStatus 401- UnAuthorized
    ExpiredJwtException
    MalformedJwtException
    SignatureException
    UnsupportedJwtException
    IllegalArgumentException
    */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            final String  requestTokenHeader = request.getHeader("Authorization");
            // Bearer asvx5dsdsda4a4sa
            log.info("requestTokenHeader {}", requestTokenHeader);
            if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

//        assert requestTokenHeader != null;
            String jwtToken = requestTokenHeader.split("Bearer ")[1];
//        String jwtToken = requestTokenHeader.substring(7).trim();

            log.info("jwtToken {}", jwtToken);

            Long userId = jwtService.getUserIdFromToken(jwtToken);
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserEntity user = userService.getUserById(userId);
                log.info("user-JwtRequestAuthFilter {} logged in", user);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                // webAuthentication details -> user information of ip:address , sessionIds, information about request,
                // to set some rate limiting, specific attacks , ipaddress details
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        }catch(Exception ex){
            handlerExceptionResolver.resolveException(request,response,null,ex);
//             throw new JwtAuthenticationException(ex.getLocalizedMessage());
        }


        // with the response

    }


}
