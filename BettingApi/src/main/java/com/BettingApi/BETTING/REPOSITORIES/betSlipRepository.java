package com.BettingApi.BETTING.REPOSITORIES;
import com.BettingApi.BETTING.ENTITIES.Bet;
import com.BettingApi.BETTING.ENTITIES.BetSlip;
import com.BettingApi.BETTING.ENTITIES.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface betSlipRepository extends JpaRepository<BetSlip, Long> {
    List<BetSlip> findByBet_Users(Users users);

    List<BetSlip> findByBet(Bet bet);
}
