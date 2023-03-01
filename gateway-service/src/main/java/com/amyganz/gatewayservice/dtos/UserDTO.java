package com.amyganz.gatewayservice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class UserDTO implements Serializable {
    private String id;
    private String fullname;
    private String email;
    private String username;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyy-MM-dd")
    private Date dob;
    private Character gender;
    private String role;
}