package com.BettingApi.BETTING.REPOSITORIES;

import com.BettingApi.BETTING.ENTITIES.Markets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface marketRepository extends JpaRepository<Markets, Long> {
}