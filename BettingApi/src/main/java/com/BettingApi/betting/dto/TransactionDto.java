package com.BettingApi.betting.dto;

import com.BettingApi.betting.entities.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionDto {

    private double amount;
    private TransactionType transactionType;
    private String transactionRef;
    private LocalDateTime transactionDate;
    private double currentBalance;
    private String phoneNumber;



}
