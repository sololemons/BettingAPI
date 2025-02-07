package com.BettingApi.BETTING.EXCEPTIONS;

public class OddsNotFoundException extends RuntimeException {
    public OddsNotFoundException(String message) {
        super(message);
    }
}
