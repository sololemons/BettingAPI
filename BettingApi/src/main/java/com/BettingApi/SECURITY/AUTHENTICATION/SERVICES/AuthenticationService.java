package com.BettingApi.SECURITY.AUTHENTICATION.SERVICES;
import com.BettingApi.BETTING.ENTITIES.Users;
import com.BettingApi.BETTING.EXCEPTIONS.MissingFieldException;
import com.BettingApi.BETTING.REPOSITORIES.userRepository;
import com.BettingApi.SECURITY.AUTHENTICATION.ENTITIES.AuthenticationRequest;
import com.BettingApi.SECURITY.AUTHENTICATION.ENTITIES.AuthenticationResponse;
import com.BettingApi.SECURITY.AUTHENTICATION.ENTITIES.RegisterRequest;
import com.BettingApi.SECURITY.CONFIGURATION.JwtService;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import lombok.RequiredArgsConstructor;
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
    private final userRepository repository;
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
        var user  = repository.findByPhoneNumber(formattedPhoneNumber)
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().
                token(jwtToken)
                .build();

    }
    public AuthenticationResponse register(RegisterRequest request){

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
            // Enforce that the number belongs to Kenya (+254)
            if (number.getCountryCode() != 254) {
                throw new MissingFieldException("Only Kenyan phone numbers (+254) are allowed!");
            }


            return phoneUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164);

        } catch (NumberParseException e) {
            throw new MissingFieldException("Invalid phone number format or it is null!");
        }
    }


}