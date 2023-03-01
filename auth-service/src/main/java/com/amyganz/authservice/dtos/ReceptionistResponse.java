package com.amyganz.authservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@JsonPropertyOrder({"id", "hotel_id", "email", "username"})
public class ReceptionistResponse implements Serializable {
    private Long id;
    @JsonProperty("hotel_id")
    private Long hotelId;
    private String email;
    private String username;
}
