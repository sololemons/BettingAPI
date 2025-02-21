package com.BettingApi.BETTING.SERVICES;

import com.BettingApi.BETTING.DTOS.BetSlipDto;
import com.BettingApi.BETTING.DTOS.UserBetslipResponseDto;
import com.BettingApi.BETTING.DTOS.UserDto;
import com.BettingApi.BETTING.ENTITIES.Bet;
import com.BettingApi.BETTING.ENTITIES.BetSlip;
import com.BettingApi.BETTING.ENTITIES.Users;
import com.BettingApi.BETTING.EXCEPTIONS.UserNotFoundException;
import com.BettingApi.BETTING.REPOSITORIES.BetRepository;
import com.BettingApi.BETTING.REPOSITORIES.BetSlipRepository;
import com.BettingApi.BETTING.REPOSITORIES.UserRepository;
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


    public UserBetslipResponseDto getBetSlipsByPhoneNumber(String phoneNumber) {

        Users user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UserNotFoundException("User not found with phone number: " + phoneNumber));


        List<BetSlip> betSlips = betSlipRepository.findByBet_Users(user);


        List<BetSlipDto> betSlipDtoList = betSlips.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        UserDto users = convertToUserDto(user);


        UserBetslipResponseDto response = new UserBetslipResponseDto();
        response.setUser(users);
        response.setBetSlips(betSlipDtoList);

        return response;
    }


    public List<BetSlipDto> getBetSlipsByPhoneNumberAndBetId(String phoneNumber, Long betId) {

        Users user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UserNotFoundException("User not found with phone number: " + phoneNumber));


        Bet bet = betRepository.findById(betId)
                .orElseThrow(() -> new UserNotFoundException("Bet not found"));


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
}
