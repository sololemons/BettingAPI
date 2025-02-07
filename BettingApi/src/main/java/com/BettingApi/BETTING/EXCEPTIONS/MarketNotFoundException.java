package com.BettingApi.BETTING.EXCEPTIONS;

public class MarketNotFoundException extends RuntimeException {
    public MarketNotFoundException(String message) {
        super(message);
    }
}
