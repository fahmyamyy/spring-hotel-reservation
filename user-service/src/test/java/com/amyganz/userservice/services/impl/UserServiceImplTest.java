package com.amyganz.userservice.services.impl;

import com.amyganz.userservice.dtos.UserRequest;
import com.amyganz.userservice.dtos.UserResponse;
import com.amyganz.userservice.entities.User;
import com.amyganz.userservice.exceptions.user.UserNotExists;
import com.amyganz.userservice.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder encoder;

    @InjectMocks
    UserServiceImpl userService = Mockito.spy(new UserServiceImpl());
    ModelMapper modelMapper = Mockito.spy(new ModelMapper());

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    List<UserResponse> userResponseDatas = new ArrayList<>(List.of(
            new UserResponse(1L,"Fahmy Malik", "fahmyyamyy@gmail.com", "fahmyamy", formatter.parse("2000-03-12"), 'M', "true"),
            new UserResponse(2L, "Alvin Septian", "alvinapiing@gmail.com", "alpinaja", formatter.parse("1999-06-13"), 'M', "true"),
            new UserResponse(3L,"Daffa Fikri", "daffafikri@gmail.com", "dapaaja", formatter.parse("1998-12-24"), 'M', "true")
    ));

    UserServiceImplTest() throws ParseException {
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addUser() throws ParseException {
        // Arrange
        UserRequest userRequest = new UserRequest();
        userRequest.setFullname("Fahmy Malik");
        userRequest.setEmail("fahmyyamyy@gmail.com");
        userRequest.setUsername("fahmyamy");
        userRequest.setPassword("Passw0rd123!");
        userRequest.setDob(formatter.parse("2000-03-12"));
        userRequest.setGender('M');

        User user = modelMapper.map(userRequest, User.class);
        UserResponse responseExpected = modelMapper.map(user, UserResponse.class);

        // Act
        UserResponse responseActual = userService.addUser(userRequest);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        // Assert
        assertEquals(responseExpected.getFullname(), responseActual.getFullname());

    }

    @Test
    void getAllUser() {
        // Arrange
        List<UserResponse> expectedUsers = userResponseDatas.stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());

        List<User> userDatas = userResponseDatas.stream()
                .map(user -> modelMapper.map(user, User.class))
                .collect(Collectors.toList());

        Mockito.when(userRepository.findAll()).thenReturn(userDatas);

        // Act
        List<UserResponse> actualUsers = userService.getAllUser();

        // Assert
        assertEquals(expectedUsers.size(), actualUsers.size());
        assertEquals(expectedUsers.toString(), actualUsers.toString());

        for (int i = 0; i < expectedUsers.size(); i++) {
            assertEquals(expectedUsers.get(i).getId(), actualUsers.get(i).getId());
            assertEquals(expectedUsers.get(i).getFullname(), actualUsers.get(i).getFullname());
            assertEquals(expectedUsers.get(i).getEmail(), actualUsers.get(i).getEmail());
            assertEquals(expectedUsers.get(i).getUsername(), actualUsers.get(i).getUsername());
            assertEquals(expectedUsers.get(i).getDob(), actualUsers.get(i).getDob());
            assertEquals(expectedUsers.get(i).getGender(), actualUsers.get(i).getGender());
            assertEquals(expectedUsers.get(i).getRole(), actualUsers.get(i).getRole());
        }
    }

    @Test
    void getUserById_andSuccess() {
        // Arrange
        User user = modelMapper.map(userResponseDatas.get(0), User.class);
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));

        // Act
        UserResponse actualResponse = userService.getUserById(1L);

        // Assert
        assertEquals(userResponseDatas.get(0).getId(), actualResponse.getId());
        assertEquals(userResponseDatas.get(0).getFullname(), actualResponse.getFullname());
        assertEquals(userResponseDatas.get(0).getEmail(), actualResponse.getEmail());
        assertEquals(userResponseDatas.get(0).getUsername(), actualResponse.getUsername());
        assertEquals(userResponseDatas.get(0).getDob(), actualResponse.getDob());
        assertEquals(userResponseDatas.get(0).getGender(), actualResponse.getGender());
        assertEquals(userResponseDatas.get(0).getRole(), actualResponse.getRole());
    }

    @Test
    void getUserById_andFail() {
        assertThrows(UserNotExists.class, () -> {
            userService.getUserById(99L);
        });
    }

    @Test
    void getUserByUsername_andSuccess() {
        // Arrange
        User user = modelMapper.map(userResponseDatas.get(0), User.class);
        Mockito.when(userRepository.findByUsername("fahmyamy")).thenReturn(Optional.of(user));

        // Act
        UserResponse actualResponse = userService.getUserByUsername("fahmyamy");

        // Assert
        assertEquals(userResponseDatas.get(0).getId(), actualResponse.getId());
        assertEquals(userResponseDatas.get(0).getFullname(), actualResponse.getFullname());
        assertEquals(userResponseDatas.get(0).getEmail(), actualResponse.getEmail());
        assertEquals(userResponseDatas.get(0).getUsername(), actualResponse.getUsername());
        assertEquals(userResponseDatas.get(0).getDob(), actualResponse.getDob());
        assertEquals(userResponseDatas.get(0).getGender(), actualResponse.getGender());
        assertEquals(userResponseDatas.get(0).getRole(), actualResponse.getRole());
    }

    @Test
    void getUserByUsername_andFail() {
        assertThrows(UserNotExists.class, () -> {
            userService.getUserByUsername("usernameasal");
        });
    }

    @Test
    void getUserByEmail_andSuccess() {
        // Arrange
        User user = modelMapper.map(userResponseDatas.get(0), User.class);
        Mockito.when(userRepository.findByEmail("fahmyyamyy@gmail.com")).thenReturn(Optional.of(user));

        // Act
        UserResponse actualResponse = userService.getUserByEmail("fahmyyamyy@gmail.com");

        // Assert
        assertEquals(userResponseDatas.get(0).getId(), actualResponse.getId());
        assertEquals(userResponseDatas.get(0).getFullname(), actualResponse.getFullname());
        assertEquals(userResponseDatas.get(0).getEmail(), actualResponse.getEmail());
        assertEquals(userResponseDatas.get(0).getUsername(), actualResponse.getUsername());
        assertEquals(userResponseDatas.get(0).getDob(), actualResponse.getDob());
        assertEquals(userResponseDatas.get(0).getGender(), actualResponse.getGender());
        assertEquals(userResponseDatas.get(0).getRole(), actualResponse.getRole());
    }

    @Test
    void getUserByEmail_andFail() {
        assertThrows(UserNotExists.class, () -> {
            userService.getUserByEmail("email@gmail.com");
        });
    }

    @Test
    void userSetAsReceptionist() {
        User user = modelMapper.map(userResponseDatas.get(0), User.class);
        user.setRole("receptionist");
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> userOptional = userRepository.findById(1L);
        User setUser = userOptional.get();

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(setUser);

        // Assert
        assertEquals(user.getRole(), setUser.getRole());

    }
}