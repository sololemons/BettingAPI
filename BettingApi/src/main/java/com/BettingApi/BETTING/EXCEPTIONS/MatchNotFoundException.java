package com.BettingApi.BETTING.EXCEPTIONS;

public class MatchNotFoundException extends RuntimeException {
    public MatchNotFoundException(String message) {
        super(message);
    }
}
