package com.BettingApi.betting.exceptions;

public class MissMatchOddsException extends RuntimeException {
    public MissMatchOddsException(String message) {
        super(message);
    }
}
