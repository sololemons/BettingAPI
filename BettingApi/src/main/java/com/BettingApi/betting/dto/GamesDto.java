package com.BettingApi.betting.dto;

import com.BettingApi.betting.entities.Markets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GamesDto {

    private Long matchId;
    private String homeTeam;
    private String awayTeam;
    private String startTime;
    private List<Markets> markets;


}
