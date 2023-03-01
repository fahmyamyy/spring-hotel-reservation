package com.amyganz.authservice.services;

import com.amyganz.authservice.dtos.*;
import org.springframework.security.core.Authentication;

public interface TokenService {
    String generatedToken(OTPRequest otpRequest);
    UserResponse decodeUserToken(String token);
//    ReceptionistResponse decodeRecptionistToken(String token);
    UserResponse addUser(UserRequest userRequest);
    void sendOtp(LoginRequest loginRequest);
    void authOtp(OTPRequest otpRequest);

//    void addReceptionist(ReceptionistRequest receptionistRequest);
}
