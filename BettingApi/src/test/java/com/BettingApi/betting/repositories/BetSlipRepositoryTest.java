package com.BettingApi.betting.repositories;

import com.BettingApi.betting.entities.Bet;
import com.BettingApi.betting.entities.BetSlip;
import com.BettingApi.betting.entities.Users;
import jakarta.transaction.Transactional;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
public class BetSlipRepositoryTest {

    @Autowired
    private BetSlipRepository betSlipRepository;

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private UserRepository userRepository;

    private Users testUser;
    private Bet testBet;
// bEFore a test make sure this is loaded
    @BeforeEach
    public void setUp() {

        testUser = new Users();
        testUser.setPhoneNumber("0706725681");
        testUser = userRepository.save(testUser);


        testBet = new Bet();
        testBet.setUsers(testUser);
        testBet = betRepository.save(testBet);

        BetSlip betSlip1 = new BetSlip();
        betSlip1.setPick("Home Team Wins");
        betSlip1.setBet(testBet);
        betSlipRepository.save(betSlip1);

        BetSlip betSlip2 = new BetSlip();
        betSlip2.setPick("Draw");
        betSlip2.setBet(testBet);
        betSlipRepository.save(betSlip2);
    }

    @Test
    public void testFindByBet_Users() {
        List<BetSlip> betSlips = betSlipRepository.findByBet_Users(testUser);



        assertThat(betSlips).isNotEmpty();
        assertThat(betSlips.size()).isEqualTo(2);
    }

    @Test
    public void testFindByBet() {
        List<BetSlip> betSlips = betSlipRepository.findByBet(testBet);



        assertThat(betSlips).isNotEmpty();
        assertThat(betSlips.size()).isEqualTo(2);
    }
}
