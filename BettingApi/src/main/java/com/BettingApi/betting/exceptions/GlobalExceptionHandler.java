package com.BettingApi.betting.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //  Handle Specific Business Exceptions
    @ExceptionHandler({MissingFieldException.class, MatchNotFoundException.class, MarketNotFoundException.class,
            OddsNotFoundException.class, InsufficientBalanceException.class})
    public ResponseEntity<Map<String, Object>> handleBadRequestExceptions(RuntimeException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage());
    }



    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedExceptions(UnauthorizedException ex) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Unauthorized", ex.getMessage());
    }



    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundExceptions(UserNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "NOT FOUND", ex.getMessage());
    }

    @ExceptionHandler(MissMatchOddsException.class)
    public ResponseEntity<Map<String, Object>> handleUnprocessedExceptions(MissMatchOddsException ex) {
        return buildErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "UNPROCESSABLE ENTITY", ex.getMessage());
    }



    //  Helper Method for Consistent Error Responses
    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String error, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", Optional.of(status.value()));
        response.put("error", error);
        response.put("message", message);
        return new ResponseEntity<>(response, status);
    }
}
