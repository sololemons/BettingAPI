package com.BettingApi.BETTING.SERVICES;

import com.BettingApi.BETTING.DTOS.BetSlipDto;
import com.BettingApi.BETTING.DTOS.UserBetslipResponseDto;
import com.BettingApi.BETTING.ENTITIES.Bet;
import com.BettingApi.BETTING.ENTITIES.BetSlip;
import com.BettingApi.BETTING.ENTITIES.Users;
import com.BettingApi.BETTING.EXCEPTIONS.UserNotFoundException;
import com.BettingApi.BETTING.REPOSITORIES.BetRepository;
import com.BettingApi.BETTING.REPOSITORIES.BetSlipRepository;
import com.BettingApi.BETTING.REPOSITORIES.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class BetSlipServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BetSlipRepository betSlipRepository;

    @Mock
    private BetRepository betRepository;

    @InjectMocks
    private BetSlipService betSlipService;

    private Users testUser;
    private Bet testBet;
    private BetSlip testBetSlip;

    @BeforeEach
    void setUp() {
        // Create a test user
        testUser = new Users();
        testUser.setPhoneNumber("0706725681");

        // Create a test bet
        testBet = new Bet();
        testBet.setBetID(10L);
        testBet.setUsers(testUser);

        // Create a test BetSlip
        testBetSlip = new BetSlip();
        testBetSlip.setBet(testBet);
        testBetSlip.setPick("Home Team Wins");

    }

    // Successful case testGetBetSlipsByPhoneNumber_Success Method
    @Test
    void testGetBetSlipsByPhoneNumber_Success() {
        when(userRepository.findByPhoneNumber("0706725681")).thenReturn(Optional.of(testUser));
        when(betSlipRepository.findByBet_Users(testUser)).thenReturn(List.of(testBetSlip));

        UserBetslipResponseDto response = betSlipService.getBetSlipsByPhoneNumber(testUser.getPhoneNumber());

        assertThat(response.getUser().getPhoneNumber()).isEqualTo(testUser.getPhoneNumber());
        assertThat(response.getBetSlips()).hasSize(1);
        assertThat(response.getBetSlips().get(0).getPick()).isEqualTo("Home Team Wins");

        verify(userRepository, times(1)).findByPhoneNumber("0706725681");
        verify(betSlipRepository, times(1)).findByBet_Users(testUser);
    }

    // User Not Found case
    @Test
    void testGetBetSlipsByPhoneNumberAndUserNotFound() {
        when(userRepository.findByPhoneNumber("0706720623")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> betSlipService.getBetSlipsByPhoneNumber("0706720623"))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found with phone number: 0706720623");

        verify(userRepository, times(1)).findByPhoneNumber("0706720623");
        verifyNoInteractions(betSlipRepository);
    }

    // Successful case for testGetBetSlipsByPhoneNumberAndBetId_Success method
    @Test
    void testGetBetSlipsByPhoneNumberAndBetId_Success() {
        when(userRepository.findByPhoneNumber(testUser.getPhoneNumber())).thenReturn(Optional.of(testUser));
        when(betRepository.findById(testBet.getBetID())).thenReturn(Optional.of(testBet));
        when(betSlipRepository.findByBet(testBet)).thenReturn(List.of(testBetSlip));

        List<BetSlipDto> betSlipDtos = betSlipService.getBetSlipsByPhoneNumberAndBetId(testUser.getPhoneNumber(), testBet.getBetID());

        assertThat(betSlipDtos).hasSize(1);
        assertThat(betSlipDtos.get(0).getPick()).isEqualTo("Home Team Wins");

        verify(userRepository, times(1)).findByPhoneNumber(testUser.getPhoneNumber());
        verify(betRepository, times(1)).findById(testBet.getBetID());
        verify(betSlipRepository, times(1)).findByBet(testBet);
    }

    //User Not Found Case
    @Test
    void testGetBetSlipsByPhoneNumberAndBetIdAndUserNotFound() {
        when(userRepository.findByPhoneNumber("0720623014")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> betSlipService.getBetSlipsByPhoneNumberAndBetId("0720623014", 20L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found with phone number: 0720623014");

        verify(userRepository, times(1)).findByPhoneNumber("0720623014");
        verifyNoInteractions(betRepository, betSlipRepository);
    }

    // Bet Not Found Case
    @Test
    void testGetBetSlipsByPhoneNumberAndBetId_BetNotFound() {
        when(userRepository.findByPhoneNumber("0710789012")).thenReturn(Optional.of(testUser));
        when(betRepository.findById(20L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> betSlipService.getBetSlipsByPhoneNumberAndBetId("0710789012", 20L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("Bet not found");

        verify(userRepository, times(1)).findByPhoneNumber("0710789012");
        verify(betRepository, times(1)).findById(20L);
        verifyNoInteractions(betSlipRepository);
    }


}
