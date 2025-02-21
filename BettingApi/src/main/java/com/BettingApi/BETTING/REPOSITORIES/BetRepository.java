package com.BettingApi.BETTING.REPOSITORIES;

import com.BettingApi.BETTING.ENTITIES.Bet;
import com.BettingApi.BETTING.ENTITIES.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {

    List<Bet> findByUsers(Users user);

}