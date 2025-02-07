package com.BettingApi.BETTING.CONTROLLER;

import com.BettingApi.BETTING.DTOS.BetDto;
import com.BettingApi.BETTING.SERVICES.betService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bets")
@RequiredArgsConstructor
public class betController {

    private final betService betService;

    @GetMapping("/user/{id}")
    public ResponseEntity<List<BetDto>> getBetsByUser(@PathVariable Long id) {
        List<BetDto> bets = betService.getBetsByUserId(id);
        return ResponseEntity.ok(bets);
    }
}
