package com.Lvprasad.SecurityAppApplication.SecurityApp;

import com.Lvprasad.SecurityAppApplication.SecurityApp.entities.UserEntity;
import com.Lvprasad.SecurityAppApplication.SecurityApp.entities.enums.Role;
import com.Lvprasad.SecurityAppApplication.SecurityApp.services.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AbstractBigDecimalAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.*;
import org.assertj.core.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@SpringBootTest
//@RequiredArgsConstructor
@Slf4j
class SecurityAppApplicationTests {

    @Autowired
    private JwtService jwtService;

    @BeforeEach
    void BeforeEach(){
        log.info("starting the method, setting up configuration: BeforeEach");
    }

    @AfterEach
    void AfterEach(){
        log.info("AfterEach method, setting up configuration or tearing Out: AfterEach");
    }

    @BeforeAll
    static void beforeAll(){
        log.info("BeforeAll");
    }

    @AfterAll
    static void afterAll(){
        log.info("AfterAll");
    }

    int addTwoNumbers(int a, int b){
        return a+b;
    }


    @Test
    void testNumberOne() {
//        log.info("Test one is run");
        int numberOne = addTwoNumbers(1,2);
//        Assertions.assertEquals(3,numberOne);

//        Assertions.assertThat(numberOne)
//                .isEqualTo(3)
//                .isCloseTo(3, Offset.offset(1));

        assertThat("Apple").isEqualTo("Apple").startsWith("App").endsWith("le").hasSize(5);
    }




    @Test
    @DisplayName("displayTestNameTwo")
    void testNumberTwo() {
        log.info("Test two is run");
    }



    @Test
    @Disabled
    void contextLoadsTest() {

        UserEntity user = new UserEntity(
                20L,
                "prasad@gmail.com",
                "Prasad#123",
                "prasad",
                Set.of(Role.USER)
        );

        String token = jwtService.generateAccessToken(user);
        System.out.println("Token = " + token);

        Long userId = jwtService.getUserIdFromToken(token);
        System.out.println("User ID = " + userId);
    }

    @Test
    @Disabled
    void BuilderContextLoads() {

        UserEntity user = UserEntity.builder()
                .id(2L)
                .email("vara@gmail.com")
                .password("vara#123")
                .name("vara")
                .roles(Set.of(Role.USER))
                .build();

        String token = jwtService.generateAccessToken(user);

        System.out.println("Token = " + token);

        Long userId = jwtService.getUserIdFromToken(token);

        System.out.println("User ID = " + userId);
    }
}
