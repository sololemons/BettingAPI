package com.BettingApi.BETTING.REPOSITORIES;
import com.BettingApi.BETTING.ENTITIES.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByPhoneNumber(String phoneNumber);
}