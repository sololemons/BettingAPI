package com.BettingApi.BETTING.REPOSITORIES;
import com.BettingApi.BETTING.ENTITIES.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface betRepository extends JpaRepository<Bet, Long> {
}