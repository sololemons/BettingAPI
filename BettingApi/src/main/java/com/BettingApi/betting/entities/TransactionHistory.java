package com.BettingApi.betting.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "transaction_history")
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "amount")
    private double amount;
    @Column(name = "transaction_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name = "transaction_ref", unique = true, nullable = false, updatable = false)
    private String transactionRef;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bet_id")
    private Bet bet;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate = LocalDateTime.now();
    @Column(name = "current_balance", nullable = false)
    private double currentBalance;


}
