package com.BettingApi.BETTING.SERVICES;
import com.BettingApi.BETTING.DTOS.*;
import com.BettingApi.BETTING.ENTITIES.*;
import com.BettingApi.BETTING.EXCEPTIONS.*;
import com.BettingApi.BETTING.REPOSITORIES.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class placeBetService {



    private  final betRepository betsRepository;
    private  final userRepository  userRepository;
    private  final oddsRepository oddRepository;
    private  final marketRepository marketsRepository;
    private  final gamesRepository gamesRepository;





    public List<BetResponseDTO> placeBets(PlaceBetRequestDTO placeBetRequestDTOS, String phoneNumber) {
        // Fetch the user from the database
        Users user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UserNotFoundException("User not found"));



        double accountBalance = user.getAccountBalance();
        double totalStake = placeBetRequestDTOS.getStake();
        int totalGames= placeBetRequestDTOS.getBets().size();



        // Validate if the user has enough balance
        if (totalStake > accountBalance) {
            throw new InsufficientBalanceException("Insufficient balance. Your account balance is " + accountBalance + ", but you need at least " + totalStake + " to place this bet.");
        }


        // Deduct stake from user's balance
        user.setAccountBalance(accountBalance - totalStake);
        userRepository.save(user);



        // Map user entity to UserDTO
        UserDto userDTO = new UserDto();
        userDTO.setId(user.getId());
        userDTO.setPhoneNumber(user.getPhoneNumber());



        List<BetResponseDTO> betResponseList = new ArrayList<>();

        // Create a new Bet object that will hold all BetSlips
        Bet bet = new Bet();
        bet.setBetPlacedOn(java.time.LocalDateTime.now().toString());
        bet.setTotalGames(totalGames);

        bet.setStake(totalStake);
        bet.setTotalOdds(0.0);
        bet.setPossibleWin(0L);
        bet.setUsers(user);


        List<BetSlip> betSlips = new ArrayList<>();

        // Iterate over each BetRequestDTO in the list
        for (BetRequestDTO betRequestDTO : placeBetRequestDTOS.getBets()) {
            // Fetch match, market, and odds based on the bet request
            MatchInfo matchInfo = getMatchInfo(betRequestDTO.getMatchId());
            String market = getMarketName(betRequestDTO.getMarketId());
            double oddsValue = getOdds(betRequestDTO.getOddsId());
            String oddType = getOddsType(betRequestDTO.getOddsId());
            System.out.println("Assigned oddType: " + oddType);

            // Create a new BetSlip and set the necessary values
            BetSlip betSlip = new BetSlip();
            betSlip.setMatchInfo(matchInfo);
            betSlip.setMarket(market);
            betSlip.setOdds(oddsValue);
            betSlip.setPick(oddType);


            // Add the BetSlip to the list of BetSlips
            betSlips.add(betSlip);

            double possibleWin = totalStake * oddsValue;

            // Update total odds and possible win
            bet.setTotalOdds(bet.getTotalOdds() + oddsValue);
            bet.setPossibleWin((long) (bet.getPossibleWin() + possibleWin));
        }

        // Set the betSlips list to the Bet entity
        bet.setBetSlips(betSlips);

        // Save the Bet entity to the database
        betsRepository.save(bet);


        // Convert BetSlip entities to BetSlipDTO
        List<BetslipDTO> betSlipDTOs = betSlips.

                stream().
                map(this::convertToBetslipDTO).
                toList();

        // Create BetResponseDTO for the bet that was placed
        BetResponseDTO betResponseDTO = BetResponseDTO.builder()
                .betID(bet.getBetID())
                .betPlacedOn(bet.getBetPlacedOn())
                .totalGames(bet.getTotalGames())
                .stake(bet.getStake())
                .totalOdds(bet.getTotalOdds())
                .possibleWin(bet.getPossibleWin())
                .betSlips(betSlipDTOs)
                .user(userDTO)
                .build();

        // Add the BetResponseDTO to the response list
        betResponseList.add(betResponseDTO);

        // Return the list of BetResponseDTOs
        return betResponseList;
    }

    // Convert BetSlip to BetSlipDTO
    private BetslipDTO convertToBetslipDTO(BetSlip betSlip) {
        BetslipDTO dto = new BetslipDTO();
        dto.setBetSlipId(betSlip.getBetSlipId());
        dto.setMatchInfo(betSlip.getMatchInfo());
        dto.setMarket(betSlip.getMarket());
        dto.setOdds(betSlip.getOdds());
        dto.setPick(betSlip.getPick());
        dto.setStatus(betStatus.PENDING_PAYOUTS);
        return dto;
    }



private String getOddsType(Long oddsId){

    var odds = oddRepository.findById(oddsId)
            .orElseThrow(() -> new OddsNotFoundException("Odds with ID " + oddsId + " not found"));
    return odds.getOddType();

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
