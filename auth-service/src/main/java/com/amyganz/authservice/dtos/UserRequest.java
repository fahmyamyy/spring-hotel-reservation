package com.amyganz.authservice.dtos;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class UserRequest implements Serializable {
    private String fullname;
    private String email;
    private String username;
    private String password;
    private Date dob;
    private Character gender;
    private String role = "user";
}

