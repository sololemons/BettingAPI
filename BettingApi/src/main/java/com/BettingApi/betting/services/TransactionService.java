package com.BettingApi.betting.services;

import com.BettingApi.betting.dto.TransactionDto;
import com.BettingApi.betting.entities.TransactionHistory;
import com.BettingApi.betting.repositories.TransactionRepository;
import com.BettingApi.security.configuration.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final JwtService jwtService;

    public List<TransactionDto> getTransactionHistory(String authHeader) {
        String token = authHeader.substring(7);
        String phoneNumber = jwtService.extractUserName(token);


        List<TransactionHistory> transactions = transactionRepository.findByUser_PhoneNumber(phoneNumber);


        return transactions.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private TransactionDto convertToDTO(TransactionHistory transaction) {
        return new TransactionDto(transaction.getAmount(), transaction.getTransactionType(),
                transaction.getTransactionRef(), transaction.getTransactionDate(), transaction.getCurrentBalance(), transaction.getUser().getPhoneNumber());
    }

}
