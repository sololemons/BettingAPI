package com.BettingApi.BETTING.UTILITIES;

import com.BettingApi.BETTING.DTOS.PlaceBetRequestDTO;
import com.BettingApi.BETTING.EXCEPTIONS.MissingFieldException;
import org.springframework.stereotype.Component;

@Component
public class BetValidationUtil {

    public void validateBetRequest(PlaceBetRequestDTO request) {
        if (request.getStake() <= 0) {
            throw new MissingFieldException("Stake cannot be zero or negative");
        }
        if (request.getBets() == null || request.getBets().isEmpty()) {
            throw new MissingFieldException("At least one BetSlip is required");
        }
    }
}
