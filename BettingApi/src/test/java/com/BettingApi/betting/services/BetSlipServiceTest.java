package com.BettingApi.betting.services;

import com.BettingApi.betting.dto.BetSlipDto;
import com.BettingApi.betting.dto.UserBetslipResponseDto;
import com.BettingApi.betting.entities.Bet;
import com.BettingApi.betting.entities.BetSlip;
import com.BettingApi.betting.entities.Users;
import com.BettingApi.betting.exceptions.UserNotFoundException;
import com.BettingApi.betting.repositories.BetRepository;
import com.BettingApi.betting.repositories.BetSlipRepository;
import com.BettingApi.betting.repositories.UserRepository;
import com.BettingApi.security.configuration.JwtService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class BetSlipServiceTest {
    @Mock
    private JwtService jwtService;

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
        testBet.setBetId(10L);
        testBet.setUsers(testUser);

        // Create a test BetSlip
        testBetSlip = new BetSlip();
        testBetSlip.setBet(testBet);
        testBetSlip.setPick("Home Team Wins");

    }

    // Successful case testGetBetSlipsByPhoneNumber_Success Method
    @Test
    void testGetBetSlipsByPhoneNumber_Success() {
        String authHeader = "mockAuthHeader";
        when(jwtService.extractUserName(authHeader.substring(7))).thenReturn(testUser.getPhoneNumber());
        when(userRepository.findByPhoneNumber("0706725681")).thenReturn(Optional.of(testUser));
        when(betSlipRepository.findByBet_Users(testUser)).thenReturn(List.of(testBetSlip));

        UserBetslipResponseDto response = betSlipService.getBetSlipsByPhoneNumber(authHeader);

        assertThat(response.getUser().getPhoneNumber()).isEqualTo(testUser.getPhoneNumber());
        assertThat(response.getBetSlips()).hasSize(1);
        assertThat(response.getBetSlips().get(0).getPick()).isEqualTo("Home Team Wins");

        verify(userRepository, times(1)).findByPhoneNumber("0706725681");
        verify(betSlipRepository, times(1)).findByBet_Users(testUser);
    }

    // User Not Found case
    @Test
    void testGetBetSlipsByPhoneNumberAndUserNotFound() {
        String authHeader = "mockAuthHeader";
        when(jwtService.extractUserName(authHeader.substring(7))).thenReturn("0706720623");
        when(userRepository.findByPhoneNumber("0706720623")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> betSlipService.getBetSlipsByPhoneNumber(authHeader))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found with phone number: 0706720623");

        verify(userRepository, times(1)).findByPhoneNumber("0706720623");
        verifyNoInteractions(betSlipRepository);
    }

    // Successful case for testGetBetSlipsByPhoneNumberAndBetId_Success method
    @Test
    void testGetBetSlipsByPhoneNumberAndBetId_Success() {
        String authHeader = "mockAuthHeader";
        when(jwtService.extractUserName(authHeader.substring(7))).thenReturn(testUser.getPhoneNumber());
        when(userRepository.findByPhoneNumber(testUser.getPhoneNumber())).thenReturn(Optional.of(testUser));
        when(betRepository.findById(testBet.getBetId())).thenReturn(Optional.of(testBet));
        when(betSlipRepository.findByBet(testBet)).thenReturn(List.of(testBetSlip));

        List<BetSlipDto> betSlipDtos = betSlipService.getBetSlipsByPhoneNumberAndBetId(authHeader, testBet.getBetId());

        assertThat(betSlipDtos).hasSize(1);
        assertThat(betSlipDtos.get(0).getPick()).isEqualTo("Home Team Wins");

        verify(userRepository, times(1)).findByPhoneNumber(testUser.getPhoneNumber());
        verify(betRepository, times(1)).findById(testBet.getBetId());
        verify(betSlipRepository, times(1)).findByBet(testBet);
    }

    //User Not Found Case
    @Test
    void testGetBetSlipsByPhoneNumberAndBetIdAndUserNotFound() {
        String authHeader = "mockAuthHeader";
        when(jwtService.extractUserName(authHeader.substring(7))).thenReturn("0720623014");
        when(userRepository.findByPhoneNumber("0720623014")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> betSlipService.getBetSlipsByPhoneNumberAndBetId(authHeader , 20L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found with phone number: 0720623014");

        verify(userRepository, times(1)).findByPhoneNumber("0720623014");
        verifyNoInteractions(betRepository, betSlipRepository);
    }

    // Bet Not Found Case
    @Test
    void testGetBetSlipsByPhoneNumberAndBetId_BetNotFound() {

        String authHeader = "mockAuthHeader";
        when(jwtService.extractUserName(authHeader.substring(7))).thenReturn(testUser.getPhoneNumber());
        when(userRepository.findByPhoneNumber(testUser.getPhoneNumber())).thenReturn(Optional.of(testUser));
        when(betRepository.findById(20L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> betSlipService.getBetSlipsByPhoneNumberAndBetId(authHeader, 20L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("Bet not found");

        verify(userRepository, times(1)).findByPhoneNumber(testUser.getPhoneNumber());
        verify(betRepository, times(1)).findById(20L);
        verifyNoInteractions(betSlipRepository);
    }


}
