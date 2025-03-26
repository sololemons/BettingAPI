package com.BettingApi.betting.controller;

import com.BettingApi.betting.dto.TransactionDto;
import com.BettingApi.betting.entities.TransactionHistory;
import com.BettingApi.betting.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/all")
    public ResponseEntity<List<TransactionDto>>getTransactionHistory() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }
    @GetMapping("/get/id")
    public ResponseEntity<List<TransactionDto>> getTransactionById(@RequestParam Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }
    @GetMapping("/search/transactionRef")
    public ResponseEntity<List<TransactionDto>> getTransactionByRef(@RequestParam String transactionRef) {
        return  ResponseEntity.ok(transactionService.getTransactionsByRef(transactionRef));
    }
}
