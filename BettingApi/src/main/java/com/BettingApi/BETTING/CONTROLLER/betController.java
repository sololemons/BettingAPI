package com.BettingApi.BETTING.CONTROLLER;

import com.BettingApi.BETTING.DTOS.BetDto;
import com.BettingApi.BETTING.SERVICES.betService;
import com.BettingApi.SECURITY.CONFIGURATION.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bets")
@RequiredArgsConstructor
public class betController {

    private final betService betService;
    private final JwtService jwtService;

    @GetMapping("/user")
    public ResponseEntity<List<BetDto>> getBetsByUser(@RequestHeader("Authorization") String authHeader) {
        // Extract JWT token from header (remove "Bearer " prefix)
        String token = authHeader.substring(7);

        // Extract phoneNumber from token
        String phoneNumber = jwtService.extractUserName(token);

        // Fetch bets using the extracted phoneNumber
        List<BetDto> bets = betService.getBetsByPhoneNumber(phoneNumber);

        return ResponseEntity.ok(bets);
    }
}
