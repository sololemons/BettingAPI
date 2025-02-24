package com.BettingApi.betting.exceptions;

public class OddsNotFoundException extends RuntimeException {
    public OddsNotFoundException(String message) {
        super(message);
    }
}
