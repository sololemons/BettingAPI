package com.BettingApi.betting.services;

import com.BettingApi.betting.dto.UserDto;
import com.BettingApi.betting.entities.Users;
import com.BettingApi.betting.exceptions.UserNotFoundException;
import com.BettingApi.betting.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    private UserDto convertToDto(Users user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setPhoneNumber(user.getPhoneNumber());
        return userDto;
    }

    public Page<UserDto> getAllUsers(int page, int size) {
        Page<Users> usersPage = userRepository.findAll(PageRequest.of(page, size));
        return usersPage.map(this::convertToDto);
    }

    public Page<UserDto> searchByPhoneNumber(String phoneNumber, int page, int size) {
        Page<Users> usersPage = userRepository.findByPhoneNumberContaining(phoneNumber, PageRequest.of(page, size));
        return usersPage.map(this::convertToDto);
    }

    public UserDto getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .map(this::convertToDto)
                .orElseThrow(() -> new UserNotFoundException("User not found with phone number: " + phoneNumber));
    }

}
