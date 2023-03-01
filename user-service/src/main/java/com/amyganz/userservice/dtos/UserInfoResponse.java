package com.amyganz.userservice.dtos;

import com.amyganz.userservice.entities.User;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@JsonPropertyOrder({"hotel_id", "user"})
public class UserInfoResponse implements Serializable {
    @JsonProperty("user")
    private UserResponse users;
    @JsonAlias("hotel_id")
    @JsonProperty("hotel_id")
    private Long hotel;
}

