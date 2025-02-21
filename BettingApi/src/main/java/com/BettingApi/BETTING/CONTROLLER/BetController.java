package com.BettingApi.BETTING.CONTROLLER;

import com.BettingApi.BETTING.DTOS.BetDto;
import com.BettingApi.BETTING.SERVICES.BetService;
import com.BettingApi.SECURITY.CONFIGURATION.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bets")
@RequiredArgsConstructor
public class BetController {

    private final BetService betService;
    private final JwtService jwtService;

    @GetMapping("/user")
    public ResponseEntity<List<BetDto>> getBetsByUser(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);


        String phoneNumber = jwtService.extractUserName(token);


        List<BetDto> bets = betService.getBetsByPhoneNumber(phoneNumber);

        return ResponseEntity.ok(bets);
    }
}
