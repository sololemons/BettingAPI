package com.BettingApi.betting.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class MatchInfo {

    private String homeTeam;
    private String awayTeam;
    private Long matchId;

}
