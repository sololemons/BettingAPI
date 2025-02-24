package com.BettingApi.betting.utility;

import com.BettingApi.betting.dto.PlaceBetRequestDto;
import com.BettingApi.betting.exceptions.MissingFieldException;
import org.springframework.stereotype.Component;

@Component
public class BetValidationUtil {

    public void validateBetRequest(PlaceBetRequestDto request) {
        if (request.getStake() <= 0) {
            throw new MissingFieldException("Stake cannot be zero or negative");
        }
        if (request.getBets() == null || request.getBets().isEmpty()) {
            throw new MissingFieldException("At least one BetSlip is required");
        }
    }
}
