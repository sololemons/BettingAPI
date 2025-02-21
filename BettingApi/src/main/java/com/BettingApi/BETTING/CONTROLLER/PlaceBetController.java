package com.BettingApi.BETTING.CONTROLLER;

import com.BettingApi.BETTING.DTOS.BetResponseDto;
import com.BettingApi.BETTING.DTOS.PlaceBetRequestDto;
import com.BettingApi.BETTING.SERVICES.PlaceBetService;
import com.BettingApi.BETTING.UTILITIES.BetValidationUtil;
import com.BettingApi.SECURITY.CONFIGURATION.JwtService;
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


        String token = authHeader.substring(7);
        String phoneNumber = jwtService.extractUserName(token);

        // Validate bet request
        betValidationUtil.validateBetRequest(placeBetRequestDTO);


        List<BetResponseDto> response = service.placeBets(placeBetRequestDTO, phoneNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
