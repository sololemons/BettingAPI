package com.BettingApi.betting.dto;

import com.BettingApi.betting.entities.BetStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BetDto {

    private Long betID;
    private String betPlacedOn;
    private int totalGames;
    private Double stake;
    private Double totalOdds;
    private BetStatus status;
    private Long possibleWin;
}
