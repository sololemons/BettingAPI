package com.BettingApi.betting.repositories;

import com.BettingApi.betting.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByPhoneNumber(String phoneNumber);

    Page<Users> findByPhoneNumberContaining(String phoneNumber, Pageable pageable);
}