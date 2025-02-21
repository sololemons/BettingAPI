package com.BettingApi.BETTING.DTOS;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PlaceBetRequestDto {
    @NotNull
    private double stake;
    @NotNull
    private List<BetRequestDto> bets;
}

