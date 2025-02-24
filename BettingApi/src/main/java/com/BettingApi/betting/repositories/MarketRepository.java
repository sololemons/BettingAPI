package com.BettingApi.betting.repositories;

import com.BettingApi.betting.entities.Markets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketRepository extends JpaRepository<Markets, Long> {
}