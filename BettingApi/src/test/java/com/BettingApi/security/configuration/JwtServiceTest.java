package com.BettingApi.security.configuration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

@Transactional
@DataJpaTest
@ExtendWith(MockitoExtension.class)
class JwtServiceTest {


    private String registrationToken;
    private String generatedToken;
    @Mock
    private UserDetails userDetails;
    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    void setUp() {


        jwtService = new JwtService();
        userDetails = new User("Solomon", "Test@123", Collections.emptyList());
        generatedToken = jwtService.generateAuthenticationToken(userDetails);
        registrationToken = jwtService.generateRegistrationToken(userDetails);

    }

    @Test
    void testIfTheExtractedUserNameIsCorrect() {
        String extractedUser = jwtService.extractUserName(generatedToken);
        assertThat(extractedUser).isEqualTo("Solomon");
    }

    @Test
    void testIfTheExtractedRegistrationTokenIsCorrect() {

        assertThat(jwtService.isRegistrationToken(registrationToken)).isTrue();
        assertThat(jwtService.isRegistrationToken(generatedToken)).isFalse();

    }

    @Test
    void testIsValidAuthenticationToken() {

        assertThat(jwtService.isValidAuthenticationToken(generatedToken)).isTrue();
        assertThat(jwtService.isValidAuthenticationToken(null)).isFalse();
        assertThat(jwtService.isValidAuthenticationToken(registrationToken)).isFalse();
    }

    @Test
    void testIsTokenValid_ShouldReturnTrue_ForValidToken() {
        String token = jwtService.generateToken(new HashMap<>(), userDetails);

        assertThat(jwtService.isTokenValid(token, userDetails)).isTrue();
    }


    @Test
    void toTestIfTheExpiredTokenWillBeCaught() {

        String expiredToken = Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject("Solomon")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() - 1000))
                .signWith(jwtService.getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

        assertThat(jwtService.isTokenValid(expiredToken, userDetails)).isFalse();
    }
    @Test
    void testIfTheWrongUsernameInTheTokenWillBeCaught() {
        String tokenWithWrongUser = Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject("WrongUserName")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(jwtService.getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

        assertThat(jwtService.isTokenValid(tokenWithWrongUser, userDetails)).isFalse();
    }







}

