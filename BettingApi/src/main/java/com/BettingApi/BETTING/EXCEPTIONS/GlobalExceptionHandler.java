package com.BettingApi.BETTING.EXCEPTIONS;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 🔹 Handle Specific Business Exceptions
    @ExceptionHandler({MissingFieldException.class, MatchNotFoundException.class, MarketNotFoundException.class,
            OddsNotFoundException.class, InsufficientBalanceException.class})
    public ResponseEntity<Map<String, Object>> handleBadRequestExceptions(RuntimeException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage());
    }



    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedExceptions(UnauthorizedException ex) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Unauthorized", ex.getMessage());
    }


    // 🔹 Handle Unauthorized Exceptions
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundExceptions(UserNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "NOT FOUND", ex.getMessage());
    }

    @ExceptionHandler(MissMatchOddsException.class)
    public ResponseEntity<Map<String, Object>> handleUnprocessedExceptions(MissMatchOddsException ex) {
        return buildErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "UNPROCESSABLE ENTITY", ex.getMessage());
    }
    // ❌ Handle "Endpoint Not Found" (404) Before Generic Exception
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Not Found", "The requested endpoint does not exist.");
    }


    //  Handle Generic Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericExceptions(Exception ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid Request", "Invalid JSON request " +
                "syntax or processing error!");
    }

    //  Helper Method for Consistent Error Responses
    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String error, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("error", error);
        response.put("message", message);
        return new ResponseEntity<>(response, status);
    }
}
