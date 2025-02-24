package com.BettingApi.betting.controller;

import com.BettingApi.betting.dto.BetResponseDto;
import com.BettingApi.betting.dto.PlaceBetRequestDto;
import com.BettingApi.betting.services.PlaceBetService;
import com.BettingApi.betting.utility.BetValidationUtil;
import com.BettingApi.security.configuration.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/place")
@RequiredArgsConstructor
public class PlaceBetController {

    private final PlaceBetService service;
    private final JwtService jwtService;
    private final BetValidationUtil betValidationUtil;

    @PostMapping("/add/bet")
    public ResponseEntity<?> placeBet(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody PlaceBetRequestDto placeBetRequestDTO) {





        List<BetResponseDto> response = service.placeBets(placeBetRequestDTO, authHeader);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
