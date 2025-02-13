package com.BettingApi.BETTING.CONTROLLER;

import com.BettingApi.BETTING.DTOS.BetResponseDTO;
import com.BettingApi.BETTING.DTOS.PlaceBetRequestDTO;

import com.BettingApi.BETTING.SERVICES.placeBetService;
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
public class placeBetController {

    private final placeBetService service;
    private final JwtService jwtService;
    private final BetValidationUtil betValidationUtil;

    @PostMapping("/add/bet")
    public ResponseEntity<?> placeBet(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody PlaceBetRequestDTO placeBetRequestDTO) {


        String token = authHeader.substring(7);
        String phoneNumber = jwtService.extractUserName(token);

        // Validate bet request
        betValidationUtil.validateBetRequest(placeBetRequestDTO);


        List<BetResponseDTO> response = service.placeBets(placeBetRequestDTO, phoneNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
