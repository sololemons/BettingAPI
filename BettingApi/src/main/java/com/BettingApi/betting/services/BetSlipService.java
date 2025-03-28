package com.BettingApi.betting.services;

import com.BettingApi.betting.dto.BetSlipDto;
import com.BettingApi.betting.dto.UserBetslipResponseDto;
import com.BettingApi.betting.dto.UserDto;
import com.BettingApi.betting.entities.Bet;
import com.BettingApi.betting.entities.BetSlip;
import com.BettingApi.betting.entities.Users;
import com.BettingApi.betting.exceptions.UserNotFoundException;
import com.BettingApi.betting.repositories.BetRepository;
import com.BettingApi.betting.repositories.BetSlipRepository;
import com.BettingApi.betting.repositories.UserRepository;
import com.BettingApi.security.configuration.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BetSlipService {

    private final UserRepository userRepository;
    private final BetSlipRepository betSlipRepository;
    private final BetRepository betRepository;
    private final JwtService jwtService;

    public UserBetslipResponseDto getBetSlipsByPhoneNumber(String authHeader) {

        String token = authHeader.substring(7);


        String phoneNumber = jwtService.extractUserName(token);

        Users user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new UserNotFoundException("User" +
                " not found with phone number: " + phoneNumber));


        List<BetSlip> betSlips = betSlipRepository.findByBet_Users(user);


        List<BetSlipDto> betSlipDtoList = betSlips.stream().map(this::convertToDto).collect(Collectors.toList());

        UserDto users = convertToUserDto(user);


        UserBetslipResponseDto response = new UserBetslipResponseDto();
        response.setUser(users);
        response.setBetSlips(betSlipDtoList);

        return response;
    }


    public List<BetSlipDto> getBetSlipsByPhoneNumberAndBetId(String authHeader, Long betId) {
        String token = authHeader.substring(7);
        String phoneNumber = jwtService.extractUserName(token);

        Users user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new UserNotFoundException("User" +
                " not found with phone number: " + phoneNumber));


        Bet bet = betRepository.findById(betId).orElseThrow(() -> new UserNotFoundException("Bet not found"));


        if (!bet.getUsers().equals(user)) {
            throw new UserNotFoundException("Bet does not belong to the user");
        }


        List<BetSlip> betSlips = betSlipRepository.findByBet(bet);


        return betSlips.stream().map(this::convertToDto).collect(Collectors.toList());
    }


    // Convert BetSlip entity to DTO
    private BetSlipDto convertToDto(BetSlip betSlip) {
        BetSlipDto betslipDTO = new BetSlipDto();
        betslipDTO.setBetSlipId(betSlip.getBetSlipId());
        betslipDTO.setMatchInfo(betSlip.getMatchInfo());
        betslipDTO.setMarket(betSlip.getMarket());
        betslipDTO.setPick(betSlip.getPick());
        betslipDTO.setOdds(betSlip.getOdds());
        betslipDTO.setStatus(betSlip.getStatus());

        return betslipDTO;
    }

    // Convert User entity to DTO
    private UserDto convertToUserDto(Users user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setPhoneNumber(user.getPhoneNumber());
        return userDto;
    }

    public List<BetSlipDto> getBetslipsByBetId(Long betId) {
        List<BetSlip> betSlips = betSlipRepository.findByBet_BetId(betId);
        return betSlips.stream().map(this::convertToDto).collect(Collectors.toList());

    }
}
