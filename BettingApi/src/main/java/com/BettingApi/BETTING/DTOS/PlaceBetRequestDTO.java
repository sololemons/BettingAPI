package com.BettingApi.BETTING.DTOS;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class PlaceBetRequestDTO {
    @NotNull
    private double stake;
    @NotNull
    private List<BetRequestDTO> bets;
}

