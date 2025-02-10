package com.BettingApi.BETTING.CONTROLLER;
import com.BettingApi.BETTING.DTOS.BetRequestDTO;
import com.BettingApi.BETTING.DTOS.BetResponseDTO;
import com.BettingApi.BETTING.SERVICES.placeBetService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/place")
@AllArgsConstructor
public class placeBetController {

    @Autowired
    private final placeBetService service;

    @PostMapping("/add/bet")
    @ResponseStatus(HttpStatus.CREATED)
    public List<BetResponseDTO> placeBets(@RequestBody List<BetRequestDTO> betRequestDTOs, @RequestParam Long id) {
        return service.placeBets(betRequestDTOs, id);

    }


}
