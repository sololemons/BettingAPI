package com.BettingApi.SECURITY.AUTHENTICATION.ENTITIES;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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