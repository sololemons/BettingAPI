package com.BettingApi.BETTING.SERVICES;
import com.BettingApi.BETTING.DTOS.BetRequestDTO;
import com.BettingApi.BETTING.DTOS.BetResponseDTO;
import com.BettingApi.BETTING.DTOS.UserDto;
import com.BettingApi.BETTING.ENTITIES.Bet;
import com.BettingApi.BETTING.ENTITIES.BetSlip;
import com.BettingApi.BETTING.ENTITIES.MatchInfo;
import com.BettingApi.BETTING.ENTITIES.Users;
import com.BettingApi.BETTING.REPOSITORIES.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        // Create BetResponseDTO for the bet that was placed
        BetResponseDTO betResponseDTO = BetResponseDTO.builder()
                .betID(bet.getBetID())
                .betPlacedOn(bet.getBetPlacedOn())
                .totalGames(bet.getTotalGames())
                .stake(bet.getStake())
                .totalOdds(bet.getTotalOdds())
                .possibleWin(bet.getPossibleWin())
                .betSlips(bet.getBetSlips())
                .user(userDTO)
                .build();

        // Add the BetResponseDTO to the response list
        betResponseList.add(betResponseDTO);

        // Return the list of BetResponseDTOs
        return betResponseList;
    }






    // Retrieve the market name from the database
    private String getMarketName(Long marketId) {
        var market = marketsRepository.findById(marketId).orElseThrow(() -> new RuntimeException("Market not found"));
        return market.getMarketName();
    }

    // Retrieve odds based on the oddsId from the database
    private double getOdds(Long oddsId) {
      var odds = oddRepository.findById(oddsId).orElseThrow(() -> new RuntimeException("Odds not found"));
        return odds.getOddsValue();
    }
    private MatchInfo getMatchInfo(Long matchId) {
        // Retrieve match information from the database based on matchId
        var game = gamesRepository.findById(matchId).orElseThrow(() -> new RuntimeException("Match not found"));

        MatchInfo matchInfo = new MatchInfo();
        matchInfo.setMatchId(game.getMatchId());
        matchInfo.setHomeTeam(game.getHomeTeam());
        matchInfo.setAwayTeam(game.getAwayTeam());

        return matchInfo;
    }



}
