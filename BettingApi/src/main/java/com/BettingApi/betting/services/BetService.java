package com.BettingApi.betting.services;

import com.BettingApi.betting.dto.BetDto;
import com.BettingApi.betting.entities.Bet;
import com.BettingApi.betting.entities.Users;
import com.BettingApi.betting.exceptions.UserNotFoundException;
import com.BettingApi.betting.repositories.BetRepository;
import com.BettingApi.betting.repositories.UserRepository;
import com.BettingApi.security.configuration.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BetService {


    private final UserRepository userRepository;
    private final BetRepository betRepository;
    private final JwtService jwtService;


    public List<BetDto> getBetsByPhoneNumber(String authHeader) {

        String token = authHeader.substring(7);


        String phoneNumber = jwtService.extractUserName(token);

        Users user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UserNotFoundException("User not found with phone number: " + phoneNumber));


        List<Bet> bets = betRepository.findByUsers(user);


        return bets.stream().map(this::convertToDto).collect(Collectors.toList());
    }


    //Method to convert Bet Entity to BetDto
    private BetDto convertToDto(Bet bet) {

        BetDto betDto = new BetDto();
        betDto.setBetID(bet.getBetId());
        betDto.setBetPlacedOn(bet.getBetPlacedOn());
        betDto.setTotalGames(bet.getTotalGames());
        betDto.setStake(bet.getStake());
        betDto.setTotalOdds(bet.getTotalOdds());
        betDto.setStatus(bet.getStatus());
        betDto.setPossibleWin(bet.getPossibleWin());

        return betDto;
    }
}
