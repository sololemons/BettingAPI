package com.BettingApi.BETTING.DTOS;


import lombok.Data;
import java.util.List;

@Data
public class UserBetslipResponseDTO {
    private UserDto user;
    private List<BetslipDTO> betSlips;
}
