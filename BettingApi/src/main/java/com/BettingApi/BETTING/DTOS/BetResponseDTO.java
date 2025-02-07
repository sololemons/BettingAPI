package com.BettingApi.BETTING.DTOS;

import com.BettingApi.BETTING.ENTITIES.BetSlip;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class BetResponseDTO {
    private Long betID;
    private String betPlacedOn;
    private int totalGames;
    private Double stake;
    private Double totalOdds;
    private Long possibleWin;
    private UserDto user;
    private List<BetSlip> betSlips;



}
