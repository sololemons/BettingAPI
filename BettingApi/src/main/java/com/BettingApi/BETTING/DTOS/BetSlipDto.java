package com.BettingApi.BETTING.DTOS;

import com.BettingApi.BETTING.ENTITIES.MatchInfo;
import com.BettingApi.BETTING.ENTITIES.BetStatus;
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
