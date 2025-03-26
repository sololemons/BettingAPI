package com.BettingApi.betting.repositories;

import com.BettingApi.betting.entities.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionHistory,Long> {

    List<TransactionHistory> findByUser_PhoneNumber(String phoneNumber);

    List<TransactionHistory> findByUser_Id(Long id);

    List<TransactionHistory> findByTransactionRef(String transactionRef);
}
