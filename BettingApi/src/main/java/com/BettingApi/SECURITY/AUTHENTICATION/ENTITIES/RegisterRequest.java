package com.BettingApi.SECURITY.AUTHENTICATION.ENTITIES;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotNull
    private String phoneNumber;
    @NotNull
    private String password;
    @NotNull
    private String confirmPassword;


}