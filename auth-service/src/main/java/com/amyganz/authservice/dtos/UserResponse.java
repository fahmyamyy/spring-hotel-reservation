package com.amyganz.authservice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class UserResponse implements Serializable {
    private Long id;
    private String fullname;
    private String email;
    private String username;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyy-MM-dd")
    private Date dob;
    private Character gender;
    private String role;
}