package com.BettingApi.BETTING.REPOSITORIES;
import com.BettingApi.BETTING.ENTITIES.Odds;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface oddsRepository extends JpaRepository<Odds, Long> {

}