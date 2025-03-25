package com.BettingApi.betting.services;

import com.BettingApi.betting.dto.GamesDto;
import com.BettingApi.betting.entities.Games;
import com.BettingApi.betting.repositories.GamesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GamesService {

    private final GamesRepository repository;

    public List<GamesDto> getAllMatches() {
        List<Games> GamesList = repository.findAll();
        return GamesList.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    private GamesDto convertToDto(Games Games) {
        GamesDto gamesDto = new GamesDto();
        gamesDto.setMatchId(Games.getMatchId());
        gamesDto.setHomeTeam(Games.getHomeTeam());
        gamesDto.setAwayTeam(Games.getAwayTeam());
        gamesDto.setStartTime(String.valueOf(Games.getStartTime()));
        gamesDto.setMarkets(Games.getMarkets());
        return gamesDto;
    }
}
