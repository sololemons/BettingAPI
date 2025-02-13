package com.BettingApi.BETTING.EXCEPTIONS;

public class MissingFieldException extends RuntimeException {
    public MissingFieldException(String message) {
        super(message);
    }
}
