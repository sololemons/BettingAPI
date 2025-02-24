package com.BettingApi.betting.repositories;

import com.BettingApi.betting.entities.Odds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OddsRepository extends JpaRepository<Odds, Long> {

}