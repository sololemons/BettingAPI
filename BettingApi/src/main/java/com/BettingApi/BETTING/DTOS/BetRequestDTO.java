package com.BettingApi.BETTING.DTOS;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class BetRequestDTO {
    @NotNull
    private Long matchId;
    @NotNull
    private Long marketId;
    @NotNull
    private Long oddsId;
    @NotNull
    private Double oddsValue;


}