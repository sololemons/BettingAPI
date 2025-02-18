package com.BettingApi.BETTING.SERVICES;

import com.BettingApi.BETTING.DTOS.BetslipDTO;
import com.BettingApi.BETTING.DTOS.UserBetslipResponseDTO;
import com.BettingApi.BETTING.DTOS.UserDto;
import com.BettingApi.BETTING.ENTITIES.Bet;
import com.BettingApi.BETTING.ENTITIES.BetSlip;
import com.BettingApi.BETTING.ENTITIES.Users;
import com.BettingApi.BETTING.EXCEPTIONS.UserNotFoundException;
import com.BettingApi.BETTING.REPOSITORIES.betRepository;
import com.BettingApi.BETTING.REPOSITORIES.betSlipRepository;
import com.BettingApi.BETTING.REPOSITORIES.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class betSlipService {

    private final userRepository userRepository;
    private final betSlipRepository betSlipRepository;
    private final betRepository betRepository;


    public UserBetslipResponseDTO getBetSlipsByPhoneNumber(String phoneNumber) {

        Users user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UserNotFoundException("User not found with phone number: " + phoneNumber));


        List<BetSlip> betSlips = betSlipRepository.findByBet_Users(user);


        List<BetslipDTO> betslipDTOList = betSlips.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        UserDto users = convertToUserDto(user);


        UserBetslipResponseDTO response = new UserBetslipResponseDTO();
        response.setUser(users);
        response.setBetSlips(betslipDTOList);

        return response;
    }




    public List<BetslipDTO> getBetSlipsByPhoneNumberAndBetId(String phoneNumber, Long betId) {

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
    private BetslipDTO convertToDto(BetSlip betSlip) {
        BetslipDTO betslipDTO = new BetslipDTO();
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
