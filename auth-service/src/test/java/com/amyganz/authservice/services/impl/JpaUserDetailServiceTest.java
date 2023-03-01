package com.amyganz.authservice.services.impl;

import com.amyganz.authservice.dtos.UserRequest;
import com.amyganz.authservice.dtos.UserResponse;
import com.amyganz.authservice.entities.User;
import com.amyganz.authservice.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JpaUserDetailServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    JpaUserDetailService userDetailService = Mockito.spy(new JpaUserDetailService());
    ModelMapper modelMapper = Mockito.spy(new ModelMapper());
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername() throws ParseException {
        // Arrange
        UserRequest userRequest = new UserRequest();
        userRequest.setFullname("Fahmy Malik");
        userRequest.setEmail("fahmyyamyy@gmail.com");
        userRequest.setUsername("fahmyamy");
        userRequest.setPassword("Passw0rd123!");
        userRequest.setDob(formatter.parse("2000-03-12"));
        userRequest.setGender('M');

        User user = modelMapper.map(userRequest, User.class);

        Mockito.when(userRepository.findByUsername("fahmyamy")).thenReturn(Optional.of(user));
        UserDetails actualResponse = userDetailService.loadUserByUsername("fahmyamy");

        assertEquals(user.getUsername(), actualResponse.getUsername());

    }
}