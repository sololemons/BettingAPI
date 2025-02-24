package com.BettingApi.security.authentication.services;

import com.BettingApi.betting.entities.Users;
import com.BettingApi.betting.exceptions.MissingFieldException;
import com.BettingApi.betting.exceptions.UserNotFoundException;
import com.BettingApi.betting.repositories.UserRepository;
import com.BettingApi.security.authentication.entities.AuthenticationRequest;
import com.BettingApi.security.authentication.entities.AuthenticationResponse;
import com.BettingApi.security.authentication.entities.RegisterRequest;
import com.BettingApi.security.configuration.JwtService;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import lombok.RequiredArgsConstructor;
import org.springframework.jmx.export.notification.UnableToSendNotificationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository repository;
    private final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        // Validate phone number before authentication
        String formattedPhoneNumber = validateAndFormatPhoneNumber(request.getPhoneNumber().trim());
        String Password = request.getPassword().trim();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        formattedPhoneNumber,
                        Password

                )
        );
        var user = repository.findByPhoneNumber(formattedPhoneNumber)
                .orElseThrow(() -> new UserNotFoundException("User not found with phone number: " + formattedPhoneNumber));
        var jwtToken = jwtService.generateAuthenticationToken(user);

        return AuthenticationResponse.builder().
                token(jwtToken)
                .build();

    }

    public AuthenticationResponse register(RegisterRequest request) {

        // Validate phone number before Registering
        String formattedPhoneNumber = validateAndFormatPhoneNumber(request.getPhoneNumber());

        String password = request.getPassword().trim();
        String confirmPassword = request.getConfirmPassword().trim();


        if (!password.equals(confirmPassword)) {
            throw new MissingFieldException("Passwords do not match!");
        }
        if (repository.findByPhoneNumber(formattedPhoneNumber).isPresent()) {
            throw new MissingFieldException("Phone number is already registered!");
        }
        var user = Users.builder()
                .phoneNumber(formattedPhoneNumber)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateRegistrationToken(user);

        return AuthenticationResponse.builder().
                token(jwtToken)
                .build();

    }

    private String validateAndFormatPhoneNumber(String phoneNumber) {
        try {
            Phonenumber.PhoneNumber number = phoneUtil.parse(phoneNumber, "KE");
            if (!phoneUtil.isValidNumber(number)) {
                throw new MissingFieldException("Invalid phone number!");
            }
            // Make sure that the number belongs to Kenya (+254)
            if (number.getCountryCode() != 254) {
                throw new MissingFieldException("Only Kenyan phone numbers (+254) are allowed!");
            }


            return phoneUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164);

        } catch (NumberParseException e) {
            throw new MissingFieldException("Invalid phone number format or it is null!");
        }
    }


}