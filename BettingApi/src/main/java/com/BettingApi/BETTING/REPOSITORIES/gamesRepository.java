package com.BettingApi.BETTING.REPOSITORIES;
import com.BettingApi.BETTING.ENTITIES.Games;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface gamesRepository extends JpaRepository<Games,Long> {
}