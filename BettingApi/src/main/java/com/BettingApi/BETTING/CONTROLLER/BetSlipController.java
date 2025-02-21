package com.BettingApi.BETTING.CONTROLLER;

import com.BettingApi.BETTING.DTOS.BetSlipDto;
import com.BettingApi.BETTING.DTOS.UserBetslipResponseDto;
import com.BettingApi.BETTING.SERVICES.BetSlipService;
import com.BettingApi.SECURITY.CONFIGURATION.JwtService;
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

        String token = authHeader.substring(7);
        String phoneNumber = jwtService.extractUserName(token);

        return ResponseEntity.ok(betSlipService.getBetSlipsByPhoneNumber(phoneNumber));
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
