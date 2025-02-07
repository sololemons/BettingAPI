package com.BettingApi.BETTING.SERVICES;
import com.BettingApi.BETTING.ENTITIES.BetSlip;
import com.BettingApi.BETTING.ENTITIES.Users;
import com.BettingApi.BETTING.REPOSITORIES.betSlipRepository;
import com.BettingApi.BETTING.REPOSITORIES.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class betSlipService {

    private final userRepository userRepository;
    private final betSlipRepository betSlipsRepository;


    public List<BetSlip> getBetSlipsByUserId(Long id) {
        Users user = userRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("User not found"));

        return betSlipsRepository.findByBet_Users(user);
    }
}