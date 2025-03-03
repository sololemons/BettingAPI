package com.BettingApi.betting.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class MatchInfo {
    @Column(name = "home_team")
    private String homeTeam;
    @Column(name = "away_team")
    private String awayTeam;
    @Column(name = "match_id")
    private Long matchId;

}
