package com.BettingApi.BETTING.DTOS;



import lombok.Data;

@Data
public class BetRequestDTO {
    private Long matchId;
    private Long marketId;
    private Long oddsId;
    private Double stake;
}