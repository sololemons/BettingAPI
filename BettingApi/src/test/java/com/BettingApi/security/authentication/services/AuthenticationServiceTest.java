package com.BettingApi.security.authentication.services;

import com.BettingApi.betting.entities.Users;
import com.BettingApi.betting.exceptions.MissingFieldException;
import com.BettingApi.betting.exceptions.UserNotFoundException;
import com.BettingApi.betting.repositories.UserRepository;
import com.BettingApi.security.authentication.entities.AuthenticationRequest;
import com.BettingApi.security.authentication.entities.AuthenticationResponse;
import com.BettingApi.security.authentication.entities.RegisterRequest;
import com.BettingApi.security.configuration.JwtService;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    private Users user;
    private AuthenticationRequest authRequest;
    private RegisterRequest registerRequest;


    @BeforeEach
    void setUp() {
        user = Users.builder()
                .phoneNumber("+254712345678")
                .password("encodedPassword")
                .build();

        authRequest = new AuthenticationRequest("+254712345678", "password");
        registerRequest = new RegisterRequest("+254712345678", "password", "password");
    }

    @Test
    void testAuthenticateIfSuccess() {
        when(userRepository.findByPhoneNumber(user.getPhoneNumber())).thenReturn(Optional.of(user));
        when(jwtService.generateAuthenticationToken(user)).thenReturn("mockJwtToken");
        AuthenticationResponse response = authenticationService.authenticate(authRequest);

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("mockJwtToken");
    }
    @Test
    void testAuthenticateIfUserIsNotFound() {
        when(userRepository.findByPhoneNumber(authRequest.getPhoneNumber())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authenticationService.authenticate(authRequest))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found with phone number: " + authRequest.getPhoneNumber());
        verify(userRepository, times(1)).findByPhoneNumber(authRequest.getPhoneNumber());
        verifyNoInteractions(jwtService);
    }

    @Test
    void testIfRegistrationSuccess() {
        when(userRepository.findByPhoneNumber(registerRequest.getPhoneNumber())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(jwtService.generateRegistrationToken(any(Users.class))).thenReturn("mockJwtToken");

        AuthenticationResponse response = authenticationService.register(registerRequest);

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("mockJwtToken");

        verify(passwordEncoder, times(1)).encode(registerRequest.getPassword());
        verify(jwtService, times(1)).generateRegistrationToken(any(Users.class));
        verify(userRepository, times(1)).save(any(Users.class));
    }

    @Test
    void testRegister_PhoneNumberAlreadyExists() {
        when(userRepository.findByPhoneNumber(registerRequest.getPhoneNumber())).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> authenticationService.register(registerRequest))
                .isInstanceOf(MissingFieldException.class)
                .hasMessage("Phone number is already registered!");
    }

    @Test
    void testRegister_PasswordsDoNotMatch() {
        RegisterRequest invalidRegisterRequest = new RegisterRequest("+254712345678", "password1", "password2");

        assertThatThrownBy(() -> authenticationService.register(invalidRegisterRequest))
                .isInstanceOf(MissingFieldException.class)
                .hasMessage("Passwords do not match!");
    }
}
