package com.amyganz.authservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class ReceptionistRequest implements Serializable {
    @JsonProperty(required = true)
    private Long hotelId;
    @JsonProperty(required = true)
    private String email;
    @JsonProperty(required = true)
    private String username;
    @JsonProperty(required = true)
    private String password;
}
