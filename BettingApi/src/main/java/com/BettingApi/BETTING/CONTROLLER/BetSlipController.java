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


// Get Betslips by UserId

@GetMapping("/user/{id}")
public ResponseEntity<UserBetslipResponseDTO> getBetSlipsByUser(@PathVariable Long id) {
    return ResponseEntity.ok(betSlipService.getBetSlipsByUserId(id));
}

    //Get  betslips by BetID

    @GetMapping("/bet/{betId}")
    public ResponseEntity<List<BetslipDTO>> getBetSlipsByBetId(@PathVariable Long betId) {
        List<BetslipDTO> betSlips = betSlipService.getBetSlipsByBetId(betId);
        return ResponseEntity.ok(betSlips);
    }

    //Get Betslips by userId and Bet Id

    @GetMapping("/user/{id}/bet/{betId}")
    public ResponseEntity<List<BetslipDTO>> getBetSlipsByUserAndBetId(
            @PathVariable Long id, @PathVariable Long betId) {
        List<BetslipDTO> betSlips = betSlipService.getBetSlipsByUserAndBetId(id, betId);
        return ResponseEntity.ok(betSlips);
    }

}
