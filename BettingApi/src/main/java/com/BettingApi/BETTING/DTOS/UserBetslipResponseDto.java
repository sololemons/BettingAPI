package com.BettingApi.BETTING.DTOS;


import lombok.Data;

import java.util.List;

@Data
public class UserBetslipResponseDto {
    private UserDto user;
    private List<BetSlipDto> betSlips;
}
