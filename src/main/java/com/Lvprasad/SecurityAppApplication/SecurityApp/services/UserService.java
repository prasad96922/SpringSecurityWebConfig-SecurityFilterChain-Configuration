package com.Lvprasad.SecurityAppApplication.SecurityApp.services;

import com.Lvprasad.SecurityAppApplication.SecurityApp.dto.LoginDTO;
import com.Lvprasad.SecurityAppApplication.SecurityApp.dto.SignUpDTO;
import com.Lvprasad.SecurityAppApplication.SecurityApp.dto.UserDTO;
import com.Lvprasad.SecurityAppApplication.SecurityApp.entities.UserEntity;
import com.Lvprasad.SecurityAppApplication.SecurityApp.exceptions.ResourceNotFoundException;
import com.Lvprasad.SecurityAppApplication.SecurityApp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authenticationManager;
//    private final JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("User not found with username: " + username));
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//                .orElseThrow(() -> new ResourceNotFoundException("User With email :"+ username + "  not found"));
    }

    public UserEntity getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User With email"+ id + "not found"));
    }

    public UserDTO signUp(SignUpDTO signUpDTO) {
       Optional<UserEntity> user =  userRepository.findByEmail(signUpDTO.getEmail());
       if(user.isPresent()){
           throw new UsernameNotFoundException("User With email"+ signUpDTO.getEmail()+"already exists");
       }

       UserEntity  toBeCreatedUser = modelMapper.map(signUpDTO,UserEntity.class);
       toBeCreatedUser.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
       return modelMapper.map(userRepository.save(toBeCreatedUser), UserDTO.class);
    }

//    public String login(LoginDTO loginDTO) {
//
//        Authentication authenticate = authenticationManager.authenticate(new
//                UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
//
//        UserEntity user = (UserEntity) authenticate.getPrincipal();
//        return jwtService.generateToken(user);
////        UserEntity user = userRepository.findByEmail(loginDTO.getEmail()).get();
//    }

}
