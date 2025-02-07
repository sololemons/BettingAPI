package com.BettingApi.BETTING.SERVICES;
import com.BettingApi.BETTING.DTOS.BetRequestDTO;
import com.BettingApi.BETTING.DTOS.BetResponseDTO;
import com.BettingApi.BETTING.DTOS.BetslipDTO;
import com.BettingApi.BETTING.DTOS.UserDto;
import com.BettingApi.BETTING.ENTITIES.*;
import com.BettingApi.BETTING.EXCEPTIONS.InsufficientBalanceException;
import com.BettingApi.BETTING.EXCEPTIONS.MarketNotFoundException;
import com.BettingApi.BETTING.EXCEPTIONS.MatchNotFoundException;
import com.BettingApi.BETTING.EXCEPTIONS.OddsNotFoundException;
import com.BettingApi.BETTING.REPOSITORIES.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class placeBetService {



    private betRepository betsRepository;
    private userRepository  userRepository;
    private oddsRepository oddRepository;
    private marketRepository marketsRepository;
    private gamesRepository gamesRepository;






    public List<BetResponseDTO> placeBets(List<BetRequestDTO> betRequestDTOs, Long id) {
        // Fetch the user from the database
        Users user = userRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("User not found"));

        double accountBalance = user.getAccountBalance(); // Get user's current balance
        double totalStake = betRequestDTOs.stream().mapToDouble(BetRequestDTO::getStake).sum();

        // Validate if the user has enough balance
        if (totalStake > accountBalance) {
            throw new InsufficientBalanceException("Insufficient balance. Your account balance is " + accountBalance + ", but you need at least " + totalStake + " to place this bet.");
        }


        // Deduct stake from user's balance
        user.setAccountBalance(accountBalance - totalStake);
        userRepository.save(user);

        // Map user entity to UserDTO
        UserDto userDTO = new UserDto();
        userDTO.setId((long) user.getId());
        userDTO.setPhoneNumber(user.getPhoneNumber());

        List<BetResponseDTO> betResponseList = new ArrayList<>();

        // Create a new Bet object that will hold all BetSlips
        Bet bet = new Bet();
        bet.setBetPlacedOn(java.time.LocalDateTime.now().toString());
        bet.setTotalGames(betRequestDTOs.size());
        bet.setStake(betRequestDTOs.stream().mapToDouble(BetRequestDTO::getStake).sum());
        bet.setTotalOdds(0.0);
        bet.setPossibleWin(0L);
        bet.setUsers(user);


        List<BetSlip> betSlips = new ArrayList<>();

        // Iterate over each BetRequestDTO in the list
        for (BetRequestDTO betRequestDTO : betRequestDTOs) {
            // Fetch match, market, and odds based on the bet request
            MatchInfo matchInfo = getMatchInfo(betRequestDTO.getMatchId());
            String market = getMarketName(betRequestDTO.getMarketId());
            double odds = getOdds(betRequestDTO.getOddsId());





            // Create a new BetSlip and set the necessary values
            BetSlip betSlip = new BetSlip();
            betSlip.setMatchInfo(matchInfo);
            betSlip.setMarket(market);
            betSlip.setOdds(odds);

            // Add the BetSlip to the list of BetSlips
            betSlips.add(betSlip);

            // Update total odds and possible win
            bet.setTotalOdds(bet.getTotalOdds() + odds); // You can adjust the logic here for total odds calculation
            bet.setPossibleWin(bet.getPossibleWin() + (long) (betRequestDTO.getStake() * odds)); // Update possible win
        }

        // Set the betSlips list to the Bet entity
        bet.setBetSlips(betSlips);

        // Save the Bet entity to the database
        betsRepository.save(bet);


        // Convert BetSlip entities to BetslipDTO
        List<BetslipDTO> betslipDTOs = betSlips.stream().map(this::convertToBetslipDTO).toList();

        // Create BetResponseDTO for the bet that was placed
        BetResponseDTO betResponseDTO = BetResponseDTO.builder()
                .betID(bet.getBetID())
                .betPlacedOn(bet.getBetPlacedOn())
                .totalGames(bet.getTotalGames())
                .stake(bet.getStake())
                .totalOdds(bet.getTotalOdds())
                .possibleWin(bet.getPossibleWin())
                .betSlips(betslipDTOs)
                .user(userDTO)
                .build();

        // Add the BetResponseDTO to the response list
        betResponseList.add(betResponseDTO);

        // Return the list of BetResponseDTOs
        return betResponseList;
    }

    // Convert BetSlip to BetslipDTO
    private BetslipDTO convertToBetslipDTO(BetSlip betSlip) {
        BetslipDTO dto = new BetslipDTO();
        dto.setBetSlipId(betSlip.getBetSlipId());
        dto.setMatchInfo(betSlip.getMatchInfo());
        dto.setMarket(betSlip.getMarket());
        dto.setOdds(betSlip.getOdds());
        dto.setStatus(betStatus.PENDING_PAYOUTS);
        return dto;
    }






    private String getMarketName(Long marketId) {
        var market = marketsRepository.findById(marketId)
                .orElseThrow(() -> new MarketNotFoundException("Market with ID " + marketId + " not found"));
        return market.getMarketName();
    }

    private double getOdds(Long oddsId) {
        var odds = oddRepository.findById(oddsId)
                .orElseThrow(() -> new OddsNotFoundException("Odds with ID " + oddsId + " not found"));
        return odds.getOddsValue();
    }

    private MatchInfo getMatchInfo(Long matchId) {
        var game = gamesRepository.findById(matchId)
                .orElseThrow(() -> new MatchNotFoundException("Match with ID " + matchId + " not found"));

        MatchInfo matchInfo = new MatchInfo();
        matchInfo.setMatchId(game.getMatchId());
        matchInfo.setHomeTeam(game.getHomeTeam());
        matchInfo.setAwayTeam(game.getAwayTeam());

        return matchInfo;
    }



}
