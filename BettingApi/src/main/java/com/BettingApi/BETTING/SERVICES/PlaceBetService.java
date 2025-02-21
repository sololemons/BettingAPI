package com.BettingApi.BETTING.SERVICES;

import com.BettingApi.BETTING.DTOS.*;
import com.BettingApi.BETTING.ENTITIES.*;
import com.BettingApi.BETTING.EXCEPTIONS.*;
import com.BettingApi.BETTING.REPOSITORIES.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
public class PlaceBetService {


    private final BetRepository betsRepository;
    private final UserRepository userRepository;
    private final OddsRepository oddRepository;
    private final MarketRepository marketsRepository;
    private final GamesRepository gamesRepository;
    private final TransactionRepository transactionRepository;


    @Transactional
    public List<BetResponseDto> placeBets(PlaceBetRequestDto placeBetRequestDTOS, String phoneNumber) {

        Users user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UserNotFoundException("User not found"));


        double accountBalance = user.getAccountBalance();
        double totalStake = placeBetRequestDTOS.getStake();
        int totalGames = placeBetRequestDTOS.getBets().size();


        // Validate if the user has enough balance
        if (totalStake > accountBalance) {
            throw new InsufficientBalanceException("Insufficient balance. Your account balance is " + accountBalance +
                    ", but you need at least " + totalStake + " to place this bet.");
        }


        // Deduct stake from user's balance
        user.setAccountBalance(accountBalance - totalStake);
        userRepository.save(user);


        // Map user entity to UserDTO
        UserDto userDTO = new UserDto();
        userDTO.setId(user.getId());
        userDTO.setPhoneNumber(user.getPhoneNumber());


        List<BetResponseDto> betResponseList = new ArrayList<>();

        // Create a new Bet object that will hold all BetSlips
        Bet bet = new Bet();
        bet.setBetPlacedOn(java.time.LocalDateTime.now().toString());
        bet.setTotalGames(totalGames);
        bet.setStake(totalStake);
        bet.setTotalOdds(0.0);
        bet.setPossibleWin(0L);
        bet.setUsers(user);


        List<BetSlip> betSlips = new ArrayList<>();
        // Set to keep track of match IDs to detect same match with different markets
        Set<Long> matchesSet = new HashSet<>();

        // Iterate over each BetRequestDTO in the list
        for (BetRequestDto betRequestDTO : placeBetRequestDTOS.getBets()) {
            // Fetch match, market, and odds based on the bet request
            MatchInfo matchInfo = getMatchInfo(betRequestDTO.getMatchId());
            String market = getMarketName(betRequestDTO.getMarketId());
            double oddsValue = getOdds(betRequestDTO.getOddsId());
            String oddType = getOddsType(betRequestDTO.getOddsId());


            // Validate that the oddsValue in the request matches the one in the database
            if (oddsValue != betRequestDTO.getOddsValue()) {

                throw new MissMatchOddsException("The odds have changed since you viewed them. " +
                        "Please refresh your page.");
            }


            if (matchesSet.contains(betRequestDTO.getMatchId())) {
                throw new MissingFieldException("You cannot place bets for multiple markets (like '1x2' and" +
                        " 'Double Chance') on the same match in a single bet.");
            }
            // Add the match to the set to track it
            matchesSet.add(betRequestDTO.getMatchId());

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
        Bet savedBet = betsRepository.save(bet);

        TransactionType transactionType = (user.getAccountBalance() > accountBalance) ? TransactionType.CREDIT : TransactionType.DEBIT;

        // Create a Transaction Object
        TransactionHistory transaction = TransactionHistory.builder().
                amount(totalStake).
                transactionDate(LocalDateTime.now()).
                transactionRef(generateRandomRef()).
                user(user).
                bet(bet).
                transactionType(transactionType).
                currentBalance(user.getAccountBalance()).
                build();


        transactionRepository.save(transaction);

        // Convert BetSlip entities to BetSlipDTO
        List<BetSlipDto> betSlipDtos = betSlips.

                stream().
                map(this::convertToBetslipDTO).
                toList();

        // Create BetResponseDTO for the bet that was placed
        BetResponseDto betResponseDTO = BetResponseDto.builder()
                .betID(savedBet.getBetId())
                .betPlacedOn(savedBet.getBetPlacedOn())
                .totalGames(savedBet.getTotalGames())
                .stake(savedBet.getStake())
                .totalOdds(savedBet.getTotalOdds())
                .possibleWin(savedBet.getPossibleWin())
                .betSlips(betSlipDtos)
                .user(userDTO)
                .build();

        // Add the BetResponseDTO to the response list
        betResponseList.add(betResponseDTO);

        // Return the list of BetResponseDTOs
        return betResponseList;
    }

    // Convert BetSlip to BetSlipDTO
    private BetSlipDto convertToBetslipDTO(BetSlip betSlip) {
        BetSlipDto dto = new BetSlipDto();
        dto.setBetSlipId(betSlip.getBetSlipId());
        dto.setMatchInfo(betSlip.getMatchInfo());
        dto.setMarket(betSlip.getMarket());
        dto.setOdds(betSlip.getOdds());
        dto.setPick(betSlip.getPick());
        dto.setStatus(BetStatus.PENDING_PAYOUTS);
        return dto;
    }

    private String generateRandomRef() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString().toUpperCase();
    }


    private String getOddsType(Long oddsId) {

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
