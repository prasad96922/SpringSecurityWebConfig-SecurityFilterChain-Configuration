package com.Lvprasad.SecurityAppApplication.SecurityApp.controllers;


import com.Lvprasad.SecurityAppApplication.SecurityApp.advice.ApiResponse;
import com.Lvprasad.SecurityAppApplication.SecurityApp.dto.LoginDTO;
import com.Lvprasad.SecurityAppApplication.SecurityApp.dto.SignUpDTO;
import com.Lvprasad.SecurityAppApplication.SecurityApp.dto.UserDTO;
import com.Lvprasad.SecurityAppApplication.SecurityApp.services.AuthService;
import com.Lvprasad.SecurityAppApplication.SecurityApp.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody SignUpDTO signUpDTO) {
        UserDTO userDTO = userService.signUp(signUpDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request,
                                                     HttpServletResponse response) {

        String jwtToken = authService.login(loginDTO);
        Cookie cookie = new Cookie("token", jwtToken);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(new ApiResponse<>(jwtToken));
    }

//    ResponseEntity<ApiResponse<String>
}
