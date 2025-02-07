package com.BettingApi.BETTING.DTOS;

import com.BettingApi.BETTING.ENTITIES.MatchInfo;
import com.BettingApi.BETTING.ENTITIES.betStatus;
import lombok.Data;

@Data
public class BetslipDTO {

    private Long betSlipId;
    private MatchInfo matchInfo;
    private String market;
    private String pick;
    private double odds;
    private betStatus status;
}
