package com.BettingApi.BETTING.DTOS;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class BetRequestDTO {
    @NotNull(message = "matchId is Required")
    private Long matchId;
    @NotNull(message = "marketId is Required")
    private Long marketId;
    @NotNull(message = "oddsId is Required")
    private Long oddsId;

}