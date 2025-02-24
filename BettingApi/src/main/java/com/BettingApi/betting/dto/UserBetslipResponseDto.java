package com.BettingApi.betting.dto;


import lombok.Data;

import java.util.List;

@Data
public class UserBetslipResponseDto {
    private UserDto user;
    private List<BetSlipDto> betSlips;
}
