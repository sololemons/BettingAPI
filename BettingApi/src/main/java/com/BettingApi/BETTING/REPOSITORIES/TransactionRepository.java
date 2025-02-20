package com.BettingApi.BETTING.REPOSITORIES;

import com.BettingApi.BETTING.ENTITIES.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionHistory,Long> {

    Optional<TransactionHistory> findByUser_PhoneNumber(String phoneNumber);

}
