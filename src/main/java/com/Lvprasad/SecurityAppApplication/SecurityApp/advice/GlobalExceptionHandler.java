package com.Lvprasad.SecurityAppApplication.SecurityApp.advice;


import com.Lvprasad.SecurityAppApplication.SecurityApp.exceptions.JwtAuthenticationException;
import com.Lvprasad.SecurityAppApplication.SecurityApp.exceptions.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.security.core.AuthenticationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException exception) {
        ApiError apiError = ApiError.builder()
                .error(exception.getLocalizedMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();

        return buildErrorResponseEntity(apiError);
    }

    private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError), apiError.getStatus());
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse<?>> handleException(Exception exception) {
//        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthenticationException(AuthenticationException exception) {
        ApiError apiError = ApiError.builder()
                .error(exception.getLocalizedMessage())
                .status(HttpStatus.UNAUTHORIZED)
                .build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<?>> JwtException(JwtException exception) {
        ApiError apiError = ApiError.builder()
                .error(exception.getLocalizedMessage())
                .status(HttpStatus.UNAUTHORIZED)
                .build();
        return buildErrorResponseEntity(apiError);
    }

//    @ExceptionHandler(JwtAuthenticationException.class)
//    public ResponseEntity<ApiResponse<?>> handleJwtException(
//            JwtAuthenticationException ex) {
//
//        ApiError apiError = ApiError.builder()
//                .error(ex.getLocalizedMessage())
//                .status(HttpStatus.UNAUTHORIZED)
//                .build();
//
//        return buildErrorResponseEntity(apiError);
//    }


}
