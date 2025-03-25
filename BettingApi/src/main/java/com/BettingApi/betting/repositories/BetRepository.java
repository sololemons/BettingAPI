package com.BettingApi.betting.repositories;

import com.BettingApi.betting.entities.Bet;
import com.BettingApi.betting.entities.Users;
import com.BettingApi.betting.services.BetSlipService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {

    List<Bet> findByUsers(Users user);



    List<Bet> findByUsers_Id(Long id);
}