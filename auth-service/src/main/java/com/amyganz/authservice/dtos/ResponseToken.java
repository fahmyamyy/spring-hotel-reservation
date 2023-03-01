package com.amyganz.authservice.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class ResponseToken {
    private String token;
}