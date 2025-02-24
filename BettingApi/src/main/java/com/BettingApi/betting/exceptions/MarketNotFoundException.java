package com.BettingApi.betting.exceptions;

public class MarketNotFoundException extends RuntimeException {
    public MarketNotFoundException(String message) {
        super(message);
    }
}
