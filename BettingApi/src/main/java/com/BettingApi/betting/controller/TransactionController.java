package com.BettingApi.betting.controller;

import com.BettingApi.betting.dto.TransactionDto;
import com.BettingApi.betting.entities.TransactionHistory;
import com.BettingApi.betting.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/get/history")
    public ResponseEntity<List<TransactionDto>> getTransaction(@RequestHeader("Authorization") String authHeader) {

        return ResponseEntity.ok(transactionService.getTransactionHistory(authHeader));
    }
}
