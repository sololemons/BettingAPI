package com.BettingApi.betting.services;

import com.BettingApi.betting.dto.BetRequestDto;
import com.BettingApi.betting.dto.BetResponseDto;
import com.BettingApi.betting.dto.PlaceBetRequestDto;
import com.BettingApi.betting.entities.*;
import com.BettingApi.betting.exceptions.InsufficientBalanceException;
import com.BettingApi.betting.exceptions.MissMatchOddsException;
import com.BettingApi.betting.repositories.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;



import static org.mockito.Mockito.*;

@Transactional
@ExtendWith(MockitoExtension.class)
class PlaceBetServiceTest {


    @Mock
    private BetRepository betsRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private OddsRepository oddRepository;
    @Mock
    private MarketRepository marketsRepository;
    @Mock
    private GamesRepository gamesRepository;
    @Mock
    private TransactionRepository transactionRepository;


    @InjectMocks
    private PlaceBetService placeBetService;


    private Users user;
    private PlaceBetRequestDto placeBetRequestDTO;
    private BetRequestDto betRequestDTO;
    private Markets markets;
    private Games games;
    private Odds odds;
    private Bet bet;
    private TransactionHistory transactionHistory;

    @BeforeEach
    void setUp() {

        user = new Users();
        user.setPhoneNumber("0706725681");
        user.setAccountBalance(1000.0);

        betRequestDTO = new BetRequestDto();
        betRequestDTO.setMatchId(1L);
        betRequestDTO.setMarketId(1L);
        betRequestDTO.setOddsId(1L);
        betRequestDTO.setOddsValue(2.0);


        placeBetRequestDTO = new PlaceBetRequestDto();
        placeBetRequestDTO.setStake(500.0);
        placeBetRequestDTO.setBets(List.of(betRequestDTO));


        odds = new Odds();
        odds.setOddsId(1L);
        odds.setOddsValue(2.0);


        games = new Games();
        games.setHomeTeam("Home Team");
        games.setAwayTeam("Away Team");
        games.setMatchId(1L);
        games.setStartTime(LocalDateTime.now());


        markets = new Markets();
        markets.setMarketId(1L);
        markets.setMarketName("1x2");
        markets.setOddsList(List.of(odds));

        bet = new Bet();
        bet.setBetId(1L);
        bet.setUsers(user);
        bet.setTotalGames(1);
        bet.setStake(placeBetRequestDTO.getStake());
        bet.setTotalOdds(2.0);

        transactionHistory = new TransactionHistory();
        transactionHistory.setBet(bet);
        transactionHistory.setAmount(placeBetRequestDTO.getStake());
        transactionHistory.setUser(user);
        transactionHistory.setTransactionType(TransactionType.DEBIT);
        transactionHistory.setCurrentBalance(user.getAccountBalance());


    }

    @Test
    void testPlaceBetsProcessSuccess() {
        when(userRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(user));
        when(oddRepository.findById(anyLong())).thenReturn(Optional.of(odds));
        when(marketsRepository.findById(anyLong())).thenReturn(Optional.of(markets));
        when(gamesRepository.findById(anyLong())).thenReturn(Optional.of(games));
        when(transactionRepository.save(any(TransactionHistory.class))).thenReturn(transactionHistory);
        when(betsRepository.save(any(Bet.class))).thenReturn(bet);

        List<BetResponseDto> response = placeBetService.placeBets(placeBetRequestDTO, user.getPhoneNumber());

        verify(userRepository, times(1)).findByPhoneNumber(anyString());
        verify(oddRepository, times(2)).findById(anyLong());
        verify(marketsRepository, times(1)).findById(anyLong());
        verify(gamesRepository, times(1)).findById(anyLong());
        verify(transactionRepository, times(1)).save(any(TransactionHistory.class));
        verify(betsRepository, times(1)).save(any(Bet.class));

        ArgumentCaptor<TransactionHistory> transactionCaptor = ArgumentCaptor.forClass(TransactionHistory.class);
        verify(transactionRepository).save(transactionCaptor.capture());
        TransactionHistory savedTransaction = transactionCaptor.getValue();

        assertThat(response).isNotNull();
        assertThat(response).hasSize(1);
        assertThat(savedTransaction.getUser().getPhoneNumber()).isEqualTo(user.getPhoneNumber());
        assertThat(savedTransaction.getTransactionType()).isEqualTo(transactionHistory.getTransactionType());
        assertThat(user.getAccountBalance()).isEqualTo(500.0);
    }
    @Test
    void testPlaceBetsFailsDueToInsufficientBalance() {
        user.setAccountBalance(100.0);
        when(userRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> placeBetService.placeBets(placeBetRequestDTO, user.getPhoneNumber()))
                .isInstanceOf(InsufficientBalanceException.class)
                .hasMessage("Insufficient balance. Your account balance is 100.0, but you need at least 500.0 to place this bet.");

        verify(userRepository, times(1)).findByPhoneNumber(anyString());
    }

    @Test
    void testPlaceBetsFailsDueToOddsMismatch() {
        when(userRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(user));
        when(gamesRepository.findById(anyLong())).thenReturn(Optional.of(new Games()));
        when(marketsRepository.findById(anyLong())).thenReturn(Optional.of(new Markets()));

        Odds changedOdds = new Odds();
        changedOdds.setOddsId(1L);
        changedOdds.setOddsValue(2.1);
        when(oddRepository.findById(anyLong())).thenReturn(Optional.of(changedOdds));

        assertThatThrownBy(() -> placeBetService.placeBets(placeBetRequestDTO, user.getPhoneNumber()))
                .isInstanceOf(MissMatchOddsException.class)
                .hasMessage("The odds have changed since you viewed them. Please refresh your page.");

        verify(userRepository, times(1)).findByPhoneNumber(anyString());
        verify(oddRepository, times(2)).findById(anyLong());
        verify(gamesRepository, times(1)).findById(anyLong());
        verify(marketsRepository, times(1)).findById(anyLong());
    }
}

