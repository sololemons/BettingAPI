package com.BettingApi.betting.repositories;

import com.BettingApi.betting.entities.Bet;
import com.BettingApi.betting.entities.Users;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
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
public class BetRepositoryTest {

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private UserRepository userRepository;

    private Users testUser;

    @BeforeEach
    public void setUp() {

        testUser = new Users();
        testUser.setPhoneNumber("0706725681");
        testUser = userRepository.save(testUser);

        Bet bet = new Bet();
        bet.setUsers(testUser);
        betRepository.save(bet);
    }




    @Test
    public void toTestFindByUsersAndReturnsBets() {

        List<Bet> retrievedBets = betRepository.findByUsers(testUser);


        Assertions.assertThat(retrievedBets).isNotEmpty();
        Assertions.assertThat(retrievedBets.size()).isEqualTo(1);
        Assertions.assertThat(retrievedBets.get(0).getUsers()).isEqualTo(testUser);
    }

    @Test
    public void testFindByUsers_NoBetsFound() {

        betRepository.deleteAll();


        List<Bet> retrievedBets = betRepository.findByUsers(testUser);

        Assertions.assertThat(retrievedBets).isEmpty();
    }
}
