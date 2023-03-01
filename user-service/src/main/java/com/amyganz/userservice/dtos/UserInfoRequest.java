package com.amyganz.userservice.dtos;

import com.amyganz.userservice.entities.User;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class UserInfoRequest implements Serializable {
    @JsonAlias("user_id")
    @JsonProperty(value = "user_id", required = true)
    private Long user;
    @JsonAlias("hotel_id")
    @JsonProperty(value = "hotel_id", required = true)
    private Long hotel;
}

