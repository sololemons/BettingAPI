package com.BettingApi.betting.repositories;

import com.BettingApi.betting.entities.Users;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.*;
import java.util.Optional;




@DataJpaTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByPhoneNumberAndUserExists() {
        // Given A user is saved in the database with a certain phone Number
        Users user = new Users();
        user.setPhoneNumber("0706725681");
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
