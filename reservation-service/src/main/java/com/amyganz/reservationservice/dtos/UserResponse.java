package com.amyganz.reservationservice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class UserResponse {
    private Long id;
    private String fullname;
    private String email;
    private String username;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyy-MM-dd")
    private Date dob;
    private Character gender;
    @JsonIgnore
    private String role;
}

