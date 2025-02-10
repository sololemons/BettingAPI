package com.BettingApi.SECURITY.AUTHENTICATION.CONTROLLERS;
import com.BettingApi.SECURITY.AUTHENTICATION.ENTITIES.AuthenticationRequest;
import com.BettingApi.SECURITY.AUTHENTICATION.ENTITIES.AuthenticationResponse;
import com.BettingApi.SECURITY.AUTHENTICATION.ENTITIES.RegisterRequest;
import com.BettingApi.SECURITY.AUTHENTICATION.SERVICES.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {

        this.service = service;
    }


    //Register an User
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }


}