package com.amyganz.authservice.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class LoginRequest {
    private String username;
    private String password;
}

