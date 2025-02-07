package com.BettingApi.SECURITY.AUTHENTICATION.ENTITIES;

import lombok.*;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String phoneNumber;
    private String password;
    private String confirmPassword;


}