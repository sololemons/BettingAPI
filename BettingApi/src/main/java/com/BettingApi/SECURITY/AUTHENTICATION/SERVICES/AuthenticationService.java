package com.BettingApi.SECURITY.AUTHENTICATION.SERVICES;
import com.BettingApi.BETTING.ENTITIES.Users;
import com.BettingApi.BETTING.REPOSITORIES.userRepository;
import com.BettingApi.SECURITY.AUTHENTICATION.ENTITIES.AuthenticationRequest;
import com.BettingApi.SECURITY.AUTHENTICATION.ENTITIES.AuthenticationResponse;
import com.BettingApi.SECURITY.AUTHENTICATION.ENTITIES.RegisterRequest;
import com.BettingApi.SECURITY.CONFIGURATION.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final userRepository repository;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getPhoneNumber(),
                        request.getPassword()

                )
        );
        var user  = repository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().
                token(jwtToken)
                .build();

    }
    public AuthenticationResponse register(RegisterRequest request){
        var user = Users.builder()
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().
                token(jwtToken)
                .build();

    }


}