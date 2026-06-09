package com.Lvprasad.SecurityAppApplication.SecurityApp.services;

import com.Lvprasad.SecurityAppApplication.SecurityApp.dto.LoginDTO;
import com.Lvprasad.SecurityAppApplication.SecurityApp.dto.LoginResponseDto;
import com.Lvprasad.SecurityAppApplication.SecurityApp.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;


    // Login-service
    public LoginResponseDto login(LoginDTO loginDTO) {


        // Remember authentication.authenticate(new UsernamePas**) to get the user data
        // from the provider from userDetailService which we implement in userEntity

        Authentication authenticate = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        UserEntity user = (UserEntity) authenticate.getPrincipal();

        log.info("userLogin {}", user);

        String accessToken = jwtService.generateAccessToken(user);

        log.info("accessToken {}", accessToken);

        String refreshToken = jwtService.generateRefreshToken(user);

        log.info("refreshToken {}", refreshToken);

        return new LoginResponseDto(user.getId(), accessToken, refreshToken);

//        return jwtService.generateToken(user);
//        UserEntity user = userRepository.findByEmail(loginDTO.getEmail()).get();
    }

    public LoginResponseDto refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        UserEntity user = userService.getUserById(userId);
        String accessToken = jwtService.generateAccessToken(user);

//        log.info("accessToken {}", accessToken);

        return new LoginResponseDto(user.getId(), accessToken, refreshToken);
    }
}
