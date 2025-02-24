package com.BettingApi.betting.controller;

import com.BettingApi.betting.dto.BetSlipDto;
import com.BettingApi.betting.dto.UserBetslipResponseDto;
import com.BettingApi.betting.services.BetSlipService;
import com.BettingApi.security.configuration.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/betslip")
public class BetSlipController {

    private final BetSlipService betSlipService;
    private final JwtService jwtService;

    public BetSlipController(BetSlipService betSlipService, JwtService jwtService) {
        this.betSlipService = betSlipService;
        this.jwtService = jwtService;
    }


    @GetMapping("/user")
    public ResponseEntity<UserBetslipResponseDto> getBetSlipsByUser(
            @RequestHeader("Authorization") String authHeader) {


        return ResponseEntity.ok(betSlipService.getBetSlipsByPhoneNumber(authHeader));
    }


    @GetMapping("/user/bet/{betId}")
    public ResponseEntity<List<BetSlipDto>> getBetSlipsByUserAndBetId(
            @RequestHeader("Authorization") String authHeader, @PathVariable Long betId) {

        String token = authHeader.substring(7);
        String phoneNumber = jwtService.extractUserName(token);

        List<BetSlipDto> betSlips = betSlipService.getBetSlipsByPhoneNumberAndBetId(phoneNumber, betId);
        return ResponseEntity.ok(betSlips);
    }
}
