package com.BettingApi.betting.dto;

import com.BettingApi.betting.entities.MatchInfo;
import com.BettingApi.betting.entities.BetStatus;
import lombok.Data;

@Data
public class BetSlipDto {

    private Long betSlipId;
    private MatchInfo matchInfo;
    private String market;
    private String pick;
    private double odds;
    private BetStatus status;

}
