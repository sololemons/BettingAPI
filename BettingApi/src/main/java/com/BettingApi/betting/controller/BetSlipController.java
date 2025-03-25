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



        List<BetSlipDto> betSlips = betSlipService.getBetSlipsByPhoneNumberAndBetId(authHeader, betId);
        return ResponseEntity.ok(betSlips);
    }
    @GetMapping("/id")
    public ResponseEntity<List<BetSlipDto>> getBetslipsByBetId(@RequestParam Long betId) {
        return ResponseEntity.ok(betSlipService.getBetslipsByBetId(betId));
    }
}
