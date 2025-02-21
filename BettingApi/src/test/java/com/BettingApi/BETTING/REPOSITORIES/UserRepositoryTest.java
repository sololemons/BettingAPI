package com.BettingApi.BETTING.REPOSITORIES;

import com.BettingApi.BETTING.ENTITIES.Users;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByPhoneNumberAndUserExists() {
        // Given A user is saved in the database with a certain phone Number
        Users user = new Users();
        user.setPhoneNumber("123456789");
        userRepository.save(user);

        // When
        Optional<Users> foundUser = userRepository.findByPhoneNumber("0706725681");

        // Then the user should be found in the Db
        assertThat(foundUser).isPresent().hasValueSatisfying(u -> {
                    assertThat(u.getPhoneNumber()).isEqualTo("0706725681");
                });
    }

    @Test
    public void testFindByPhoneNumberAndUserDoesNotExist() {
        //When
        Optional<Users> foundUser = userRepository.findByPhoneNumber("0706725680");

        //Then
        assertThat(foundUser).isEmpty();
    }
}
