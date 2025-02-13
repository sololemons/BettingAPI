package com.BettingApi.BETTING.SERVICES;

import com.BettingApi.BETTING.DTOS.BetDto;
import com.BettingApi.BETTING.DTOS.UserBetslipResponseDTO;
import com.BettingApi.BETTING.DTOS.UserDto;
import com.BettingApi.BETTING.ENTITIES.Bet;
import com.BettingApi.BETTING.ENTITIES.Users;
import com.BettingApi.BETTING.EXCEPTIONS.UserNotFoundException;
import com.BettingApi.BETTING.REPOSITORIES.betRepository;
import com.BettingApi.BETTING.REPOSITORIES.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class betService {

    private final userRepository userRepository;
    private final betRepository betRepository;

    public List<BetDto> getBetsByUserId(Long id) {
        // Fetch user by ID
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Get user's bets
        List<Bet> bets = betRepository.findByUsers(user);

        // Convert bets to BetDto
        return bets.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private BetDto convertToDto(Bet bet) {
        BetDto betDto = new BetDto();
        betDto.setBetID(bet.getBetID());
        betDto.setBetPlacedOn(bet.getBetPlacedOn());
        betDto.setTotalGames(bet.getTotalGames());
        betDto.setStake(bet.getStake());
        betDto.setTotalOdds(bet.getTotalOdds());
        betDto.setStatus(bet.getStatus());
        betDto.setPossibleWin(bet.getPossibleWin());


        return betDto;
    }


}
