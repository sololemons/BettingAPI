package com.BettingApi.BETTING.CONTROLLER;

import com.BettingApi.BETTING.DTOS.BetslipDTO;
import com.BettingApi.BETTING.DTOS.UserBetslipResponseDTO;
import com.BettingApi.BETTING.SERVICES.betSlipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/betslip")
public class BetSlipController {

    private final betSlipService betSlipService;

    public BetSlipController(betSlipService betSlipService) {
        this.betSlipService = betSlipService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getBetSlipsByUser(@PathVariable Long id) {
        try {
            UserBetslipResponseDTO response = betSlipService.getBetSlipsByUserId(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/bet/{betId}")
    public ResponseEntity<List<BetslipDTO>> getBetSlipsByBetId(@PathVariable Long betId) {
        List<BetslipDTO> betSlips = betSlipService.getBetSlipsByBetId(betId);
        return ResponseEntity.ok(betSlips);
    }
    @GetMapping("/user/{id}/bet/{betId}")
    public ResponseEntity<List<BetslipDTO>> getBetSlipsByUserAndBetId(
            @PathVariable Long id, @PathVariable Long betId) {
        List<BetslipDTO> betSlips = betSlipService.getBetSlipsByUserAndBetId(id, betId);
        return ResponseEntity.ok(betSlips);
    }

}
