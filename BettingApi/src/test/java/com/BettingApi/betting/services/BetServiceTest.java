package com.BettingApi.betting.services;

import com.BettingApi.betting.dto.BetDto;
import com.BettingApi.betting.entities.Bet;
import com.BettingApi.betting.entities.Users;
import com.BettingApi.betting.exceptions.UserNotFoundException;
import com.BettingApi.betting.repositories.BetRepository;
import com.BettingApi.betting.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
public class BetServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BetRepository betRepository;

    @InjectMocks
    private BetService betService;

    private Users testUser;
    private Bet testBet;

    @BeforeEach
    void setUp() {
        testUser = new Users();
        testUser.setPhoneNumber("0706725681");

        testBet = new Bet();
        testBet.setBetId(1L);
        testBet.setUsers(testUser);
        testBet.setTotalGames(2);
        testBet.setStake(100D);
        testBet.setTotalOdds(2.5);
        testBet.setPossibleWin(250L);
    }

    @Test
    void testGetBetsByPhoneNumberAndSuccess() {
        when(userRepository.findByPhoneNumber(testUser.getPhoneNumber())).thenReturn(Optional.of(testUser));
        when(betRepository.findByUsers(testUser)).thenReturn(Collections.singletonList(testBet));

        List<BetDto> betDto = betService.getBetsByPhoneNumber(testUser.getPhoneNumber());


        assertThat(betDto).isNotEmpty();
        assertThat(betDto.size()).isEqualTo(1);
       assertThat(betDto.get(0).getBetID()).isEqualTo(1L);

        verify(userRepository, times(1)).findByPhoneNumber(testUser.getPhoneNumber());
        verify(betRepository, times(1)).findByUsers(testUser);
    }

    @Test
    void testGetBetsByPhoneNumberAndUserNotFound() {
        when(userRepository.findByPhoneNumber("0706725680")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> betService.getBetsByPhoneNumber("0706725680"))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found with phone number: 0706725680");

        verify(userRepository, times(1)).findByPhoneNumber("0706725680");
        verifyNoInteractions(betRepository);
    }
}
