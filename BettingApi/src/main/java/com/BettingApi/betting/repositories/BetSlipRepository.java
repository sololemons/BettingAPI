package com.BettingApi.betting.repositories;

import com.BettingApi.betting.entities.Bet;
import com.BettingApi.betting.entities.BetSlip;
import com.BettingApi.betting.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BetSlipRepository extends JpaRepository<BetSlip, Long> {
    List<BetSlip> findByBet_Users(Users users);

    List<BetSlip> findByBet(Bet bet);
}
