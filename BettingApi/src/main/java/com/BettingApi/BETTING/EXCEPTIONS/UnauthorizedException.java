package com.BettingApi.BETTING.EXCEPTIONS;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
