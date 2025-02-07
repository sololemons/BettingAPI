package com.BettingApi.BETTING.EXCEPTIONS;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
