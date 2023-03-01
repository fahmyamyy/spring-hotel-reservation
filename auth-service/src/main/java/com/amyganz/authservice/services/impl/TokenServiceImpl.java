package com.amyganz.authservice.services.impl;

import com.amyganz.authservice.dtos.*;
//import com.amyganz.authservice.entities.Receptionist;
//import com.amyganz.authservice.entities.Receptionist;
import com.amyganz.authservice.entities.User;
//import com.amyganz.authservice.repositories.ReceptionistRepository;
import com.amyganz.authservice.exceptions.auth.*;
import com.amyganz.authservice.repositories.UserRepository;
import com.amyganz.authservice.services.TokenService;
import com.amyganz.authservice.utils.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
//@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    @Autowired
    private JwtEncoder jwtEncoder;
    @Autowired
    private JwtDecoder jwtDecoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private KafkaTemplate<String, String> template;
    private UserResponse convertToResponse(User user) {return modelMapper.map(user, UserResponse.class);}

    @Override
    public String generatedToken(OTPRequest otpRequest) {
        Instant now = Instant.now();
//        String scope = authentication
//                .getAuthorities()
//                .stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(""));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(otpRequest.getUsername())
                .claim("scope", otpRequest.getUsername())
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Override
    public UserResponse decodeUserToken(String token) {
        String newToken = token.split(" ")[1];
        Jwt jwtToken = jwtDecoder.decode(newToken);
        String data = jwtToken.getSubject();
        Optional<User> usernameOptional = userRepository.findByUsername(data);
        if (!usernameOptional.isPresent()) {
            throw new InvalidCredentials();
        }
        return modelMapper.map(usernameOptional.get(), UserResponse.class);
    }

    @Override
    public void sendOtp(LoginRequest loginRequest) {
        Optional<User> usernameOptional = userRepository.findByUsername(loginRequest.getUsername());
        if (!usernameOptional.isPresent()) {
            throw new InvalidCredentials();
        }

        User user = usernameOptional.get();

        Random rand = new Random();
        String otp = "";
        for (int i = 0; i < 6; i++) {
            int num = rand.nextInt(9); // generates a random number between 0 and 99
            otp += String.valueOf(num);
        }

        OTPEmail otpEmail = new OTPEmail();
        otpEmail.setUsername(loginRequest.getUsername());
        otpEmail.setEmail(user.getEmail());
        otpEmail.setOtp(otp);

        template.send("otp", otpEmail.toString());

        user.setOtp(otp);
        user.setOtpExpired(LocalDateTime.now().plusMinutes(10));
        userRepository.save(user);
    }

    @Override
    public void authOtp(OTPRequest otpRequest) {
        Optional<User> usernameOptional = userRepository.findByUsername(otpRequest.getUsername());
        if (!usernameOptional.get().getOtp().equals(otpRequest.getOtp())) {
            throw new InvalidOTP();
        } else if (usernameOptional.get().getOtpExpired().isBefore(LocalDateTime.now())) {
            throw new InvalidOTPExpired();
        }
    }

//    @Override
//    @Transactional
//    public void addUser(UserRequest userRequest) {
//        User user = modelMapper.map(userRequest, User.class);
//        if (!PasswordValidator.isValid(user.getPassword())) {
//            throw new InvalidPassword();
//
//        }
//        user.setPassword(encoder.encode(user.getPassword()));
//        userRepository.save(user);
//    }

    @Transactional
    @Override
    public UserResponse addUser(UserRequest userRequest) {
        Optional<User> usernameOptional = userRepository.findByUsername(userRequest.getUsername());
        if (usernameOptional.isPresent()) {
            throw new UserUsernameAlreadyExists();
        }

        Optional<User> emailOptional = userRepository.findByEmail(userRequest.getEmail());
        if (emailOptional.isPresent()) {
            throw new UserEmailAlreadyExists();
        }

        LocalDate birthdateLocal = userRequest.getDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period period = Period.between(birthdateLocal, LocalDate.now());
        int years = period.getYears();

        if (years < 18) {
            throw new UserUnderage();
        }

        User user = modelMapper.map(userRequest, User.class);
        if (!PasswordValidator.isValid(user.getPassword())) {
            throw new InvalidPassword();

        }
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return convertToResponse(user);
    }
}
