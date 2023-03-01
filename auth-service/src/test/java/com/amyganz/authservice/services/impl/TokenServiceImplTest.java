package com.amyganz.authservice.services.impl;

import com.amyganz.authservice.dtos.OTPRequest;
import com.amyganz.authservice.dtos.UserRequest;
import com.amyganz.authservice.entities.User;
import com.amyganz.authservice.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    JwtDecoder jwtDecoder;
    @Mock
    JwtEncoder jwtEncoder;
    @Mock
    PasswordEncoder encoder;

    @InjectMocks
    TokenServiceImpl tokenService = Mockito.spy(new TokenServiceImpl());
    ModelMapper modelMapper = Mockito.spy(new ModelMapper());
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generatedToken() {
    }

    @Test
    void decodeUserToken() {
    }

    @Test
    void sendOtp() {
    }

    @Test
    void authOtp() throws ParseException {
        UserRequest userRequest = new UserRequest();
        userRequest.setFullname("Fahmy Malik");
        userRequest.setEmail("fahmyyamyy@gmail.com");
        userRequest.setUsername("fahmyamy");
        userRequest.setPassword("Passw0rd123!");
        userRequest.setDob(formatter.parse("2000-03-12"));
        userRequest.setGender('M');

        User user = modelMapper.map(userRequest, User.class);
        user.setOtpExpired(LocalDateTime.now().plusMinutes(10));
        user.setOtp("123123");

        Mockito.when(userRepository.findByUsername("fahmyamy")).thenReturn(Optional.of(user));

        tokenService.authOtp(new OTPRequest("fahmyamy", "123123"));

        assertEquals(user.getOtp(), "123123");
        assertEquals(user.getUsername(), "fahmyamy");

    }

    @Test
    void addUser() {
    }
}