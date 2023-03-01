package com.amyganz.authservice.controllers;

import com.amyganz.authservice.dtos.*;
import com.amyganz.authservice.services.TokenService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody UserRequest userRequest) {
        UserResponse response = tokenService.addUser(userRequest);
        return new ResponseEntity<ResponseDTO>(ResponseDTO.builder()
                .httpStatus(HttpStatus.CREATED)
                .status(true)
                .message("Successfully register new User")
                .data(response).build(), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<String>> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        tokenService.sendOtp(loginRequest);
        return new ResponseEntity(ResponseDTO.builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("OTP has been sent to your Email")
                .data(Collections.emptyList())
                .build(), HttpStatus.OK);

    }

    @PostMapping("/login/otp")
    public ResponseEntity<ResponseDTO<String>> otp(@RequestBody OTPRequest otpRequest) {
        tokenService.authOtp(otpRequest);
        String token = tokenService.generatedToken(otpRequest);
        return new ResponseEntity(ResponseDTO.builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Token granted")
                .data(new ResponseToken(token))
                .build(), HttpStatus.OK);

    }

    @GetMapping("/info")
    public ResponseEntity<ResponseDTO<Object>> userInfo(@RequestHeader(name = "Authorization") String tokenBearer) {
        UserResponse user = tokenService.decodeUserToken(tokenBearer);

        return new ResponseEntity(ResponseDTO.builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Token verified")
                .data(user)
                .build(), HttpStatus.OK);
    }
}
