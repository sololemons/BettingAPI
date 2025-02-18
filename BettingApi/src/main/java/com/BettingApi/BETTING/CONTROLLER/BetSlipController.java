package com.BettingApi.BETTING.CONTROLLER;

import com.BettingApi.BETTING.DTOS.BetslipDTO;
import com.BettingApi.BETTING.DTOS.UserBetslipResponseDTO;
import com.BettingApi.BETTING.SERVICES.betSlipService;
import com.BettingApi.SECURITY.CONFIGURATION.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/betslip")
public class BetSlipController {

    private final betSlipService betSlipService;
    private final JwtService jwtService;

    public BetSlipController(betSlipService betSlipService, JwtService jwtService) {
        this.betSlipService = betSlipService;
        this.jwtService = jwtService;
    }


    @GetMapping("/user")
    public ResponseEntity<UserBetslipResponseDTO> getBetSlipsByUser(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        String phoneNumber = jwtService.extractUserName(token);

        return ResponseEntity.ok(betSlipService.getBetSlipsByPhoneNumber(phoneNumber));
    }



    @GetMapping("/user/bet/{betId}")
    public ResponseEntity<List<BetslipDTO>> getBetSlipsByUserAndBetId(
            @RequestHeader("Authorization") String authHeader, @PathVariable Long betId) {

        String token = authHeader.substring(7);
        String phoneNumber = jwtService.extractUserName(token);

        List<BetslipDTO> betSlips = betSlipService.getBetSlipsByPhoneNumberAndBetId(phoneNumber, betId);
        return ResponseEntity.ok(betSlips);
    }
}
