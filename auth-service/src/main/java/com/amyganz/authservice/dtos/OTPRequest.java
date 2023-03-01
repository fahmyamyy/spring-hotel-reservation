package com.amyganz.authservice.dtos;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class OTPRequest implements Serializable {
    private String username;
    private String otp;
}
