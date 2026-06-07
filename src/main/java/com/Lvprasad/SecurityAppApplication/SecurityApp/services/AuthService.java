package com.Lvprasad.SecurityAppApplication.SecurityApp.services;

import com.Lvprasad.SecurityAppApplication.SecurityApp.dto.LoginDTO;
import com.Lvprasad.SecurityAppApplication.SecurityApp.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String login(LoginDTO loginDTO) {


        // Remember authentication.authenticate(new UsernamePas**) to get the user data
        // from the provider from userDetailService which we implement in userEntity

        Authentication authenticate = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        UserEntity user = (UserEntity) authenticate.getPrincipal();
        return jwtService.generateToken(user);
//        UserEntity user = userRepository.findByEmail(loginDTO.getEmail()).get();
    }
}
