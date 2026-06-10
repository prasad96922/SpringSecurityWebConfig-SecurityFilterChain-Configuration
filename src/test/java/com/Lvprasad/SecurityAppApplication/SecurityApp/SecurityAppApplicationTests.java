package com.Lvprasad.SecurityAppApplication.SecurityApp;

import com.Lvprasad.SecurityAppApplication.SecurityApp.entities.UserEntity;
import com.Lvprasad.SecurityAppApplication.SecurityApp.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
//@RequiredArgsConstructor
class SecurityAppApplicationTests {

    @Autowired
    private JwtService jwtService;

    @Test
    void contextLoads() {
        UserEntity user = new UserEntity(2L, "vara@gmail.com", "vara#123", "vara");

        String token = jwtService.generateAccessToken(user);
        System.out.println("Token ID==============>" + token);

        Long userId = jwtService.getUserIdFromToken(token);
        //Error:[JWT expired 451280 milliseconds ago at 2026-06-07T04:42:13.000Z. Current time: 2026-06-07T04:49:44.280Z. Allowed clock skew: 0]
        System.out.println("User ID==============>" + userId);

    }
}
