package com.BettingApi.BETTING.DTOS;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BetResponseDto {
    private Long betID;
    private String betPlacedOn;
    private int totalGames;
    private double stake;
    private double totalOdds;
    private Long possibleWin;
    private UserDto user;
    private List<BetSlipDto> betSlips;



}
