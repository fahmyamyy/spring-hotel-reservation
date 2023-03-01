package com.amyganz.reservationservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class EmailRequest implements Serializable {
    @JsonProperty(required = true)
    private String to;
    @JsonProperty(required = true)
    private String otp;
}