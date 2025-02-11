package com.BettingApi.BETTING.CONTROLLER;
import com.BettingApi.BETTING.DTOS.BetRequestDTO;
import com.BettingApi.BETTING.DTOS.BetResponseDTO;
import com.BettingApi.BETTING.SERVICES.placeBetService;
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

    @PostMapping("/add/bet")
    public ResponseEntity<List<BetResponseDTO>> placeBets(@RequestBody List<BetRequestDTO> betRequestDTOs, @RequestParam Long id) {
        List<BetResponseDTO> response = service.placeBets(betRequestDTOs, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



}
