package com.BettingApi.betting.controller;

import com.BettingApi.betting.dto.GamesDto;
import com.BettingApi.betting.services.GamesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GamesController {
    private final GamesService gamesService;

    @GetMapping("/all")
    public List<GamesDto> getAllGames() {
        return gamesService.getAllMatches();
    }

}
