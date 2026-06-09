package com.Lvprasad.SecurityAppApplication.SecurityApp.controllers;


import com.Lvprasad.SecurityAppApplication.SecurityApp.advice.ApiResponse;
import com.Lvprasad.SecurityAppApplication.SecurityApp.dto.LoginDTO;
import com.Lvprasad.SecurityAppApplication.SecurityApp.dto.LoginResponseDto;
import com.Lvprasad.SecurityAppApplication.SecurityApp.dto.SignUpDTO;
import com.Lvprasad.SecurityAppApplication.SecurityApp.dto.UserDTO;
import com.Lvprasad.SecurityAppApplication.SecurityApp.services.AuthService;
import com.Lvprasad.SecurityAppApplication.SecurityApp.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @Value("${deploy.env}")
    private String deployEnv;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody SignUpDTO signUpDTO) {
        UserDTO userDTO = userService.signUp(signUpDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request,
                                                     HttpServletResponse response) {

        LoginResponseDto loginResponseDto = authService.login(loginDTO);


        Cookie cookie = new Cookie("refreshToken", loginResponseDto.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv));
        response.addCookie(cookie);
        return ResponseEntity.ok(new ApiResponse<>(loginResponseDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponseDto>> refresh(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        String refreshToken = Arrays.stream(cookies)
                .filter(cookie ->  "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(()-> new AuthenticationServiceException("Invalid refreshToken"));


        LoginResponseDto loginResponseDto = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(new ApiResponse<>(loginResponseDto));
    }

//    ResponseEntity<ApiResponse<String>
}
