package com.BettingApi.BETTING.SERVICES;

import com.BettingApi.BETTING.DTOS.BetDto;
import com.BettingApi.BETTING.ENTITIES.Bet;
import com.BettingApi.BETTING.ENTITIES.Users;
import com.BettingApi.BETTING.EXCEPTIONS.UserNotFoundException;
import com.BettingApi.BETTING.REPOSITORIES.BetRepository;
import com.BettingApi.BETTING.REPOSITORIES.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BetService {

    private final UserRepository userRepository;
    private final BetRepository betRepository;


    public List<BetDto> getBetsByPhoneNumber(String phoneNumber) {

        Users user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UserNotFoundException("User not found with phone number: " + phoneNumber));


        List<Bet> bets = betRepository.findByUsers(user);


        return bets.stream().map(this::convertToDto).collect(Collectors.toList());
    }


    //Method to convert Bet Entity to BetDto
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
