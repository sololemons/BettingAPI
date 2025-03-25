package com.BettingApi.betting.controller;

import com.BettingApi.betting.dto.UserDto;
import com.BettingApi.betting.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<Page<UserDto>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<UserDto> userDtoPage = userService.getAllUsers(page, size);
        return ResponseEntity.ok(userDtoPage);
    }
    @GetMapping("/search")
    public ResponseEntity<Page<UserDto>> searchByPhoneNumber(
            @RequestParam String phoneNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<UserDto> userDtoPage = userService.searchByPhoneNumber(phoneNumber, page, size);
        return ResponseEntity.ok(userDtoPage);
    }
    @GetMapping("/phoneNumber")
    public UserDto getUserByPhoneNumber(@RequestParam String phoneNumber) {
        return userService.getUserByPhoneNumber(phoneNumber);
    }
}
