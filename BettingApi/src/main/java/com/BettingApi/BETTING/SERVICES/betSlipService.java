package com.BettingApi.BETTING.SERVICES;


import com.BettingApi.BETTING.DTOS.BetslipDTO;
import com.BettingApi.BETTING.DTOS.UserBetslipResponseDTO;
import com.BettingApi.BETTING.DTOS.UserDto;
import com.BettingApi.BETTING.ENTITIES.Bet;
import com.BettingApi.BETTING.ENTITIES.BetSlip;
import com.BettingApi.BETTING.ENTITIES.Users;

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
    private  final betRepository betRepository;


    public UserBetslipResponseDTO getBetSlipsByUserId(Long id) {
        // Fetch user by ID
        Users user = userRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Get the user's bet slips
        List<BetSlip> betSlips = betSlipRepository.findByBet_Users(user);

        // Convert BetSlip entities to DTOs
        List<BetslipDTO> betslipDTOList = betSlips.stream().map(this::convertToDto).collect(Collectors.toList());

        // Create response DTO
        UserBetslipResponseDTO response = new UserBetslipResponseDTO();
        response.setUser(convertToUserDto(user));
        response.setBetSlips(betslipDTOList);

        return response;
    }

    //Method To Convert Betslip to DTO

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
//Method to Convert User Entity to DTO
    private UserDto convertToUserDto(Users user) {
        UserDto userDto = new UserDto();
        userDto.setId((long) user.getId());
        userDto.setPhoneNumber(user.getPhoneNumber());
        return userDto;
    }


    public List<BetslipDTO> getBetSlipsByBetId(Long betId) {
        // Fetch the bet by ID
        Bet bet = betRepository.findById(betId)
                .orElseThrow(() -> new RuntimeException("Bet not found"));

        // Get bet slips associated with the bet
        List<BetSlip> betSlips = betSlipRepository.findByBet(bet);

        // Convert to DTO
        return betSlips.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<BetslipDTO> getBetSlipsByUserAndBetId(Long id, Long betId) {
        // Fetch the user
        Users user = userRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch the bet
        Bet bet = betRepository.findById(betId)
                .orElseThrow(() -> new RuntimeException("Bet not found"));

        // Ensure the bet belongs to the user
        if (!bet.getUsers().equals(user)) {
            throw new RuntimeException("Bet does not belong to the user");
        }

        // Get bet slips associated with the bet
        List<BetSlip> betSlips = betSlipRepository.findByBet(bet);

        // Convert to DTO
        return betSlips.stream().map(this::convertToDto).collect(Collectors.toList());
    }

}

