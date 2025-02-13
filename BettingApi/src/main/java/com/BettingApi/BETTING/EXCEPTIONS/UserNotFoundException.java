package com.BettingApi.BETTING.EXCEPTIONS;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
