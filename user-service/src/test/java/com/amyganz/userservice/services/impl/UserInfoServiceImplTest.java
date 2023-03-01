package com.amyganz.userservice.services.impl;

import com.amyganz.userservice.dtos.UserInfoResponse;
import com.amyganz.userservice.dtos.UserResponse;
import com.amyganz.userservice.entities.User;
import com.amyganz.userservice.entities.UserInfo;
import com.amyganz.userservice.repositories.UserInfoRepository;
import com.amyganz.userservice.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserInfoServiceImplTest {
    @Mock
    UserInfoRepository userInfoRepository;
    @Mock
    PasswordEncoder encoder;

    @InjectMocks
    UserInfoServiceImpl userInfoService = Mockito.spy(new UserInfoServiceImpl());
    ModelMapper modelMapper = Mockito.spy(new ModelMapper());

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    UserResponse dummyResponse = new UserResponse(1L,"Fahmy Malik", "fahmyyamyy@gmail.com", "fahmyamy", formatter.parse("2000-03-12"), 'M', "true");

//    List<UserInfoResponse> userResponseDatas = new ArrayList<>(List.of(
//            new UserInfoResponse(dummyResponse, 1L)
//    ));

    UserInfoResponse userInfoResponse = new UserInfoResponse(dummyResponse, 1L);

    UserInfoServiceImplTest() throws ParseException {
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserInfoByUserId() {
        UserInfoResponse expectedResponse = userInfoResponse;

        UserInfo user = new UserInfo();
        user.setUsers(modelMapper.map(expectedResponse.getUsers(), User.class));
        user.setHotel(expectedResponse.getHotel());
        Mockito.when(userInfoRepository.findById(1L)).thenReturn(Optional.of(user));

        UserInfoResponse actualResponse = userInfoService.getUserInfoByUserId(1L);

        assertEquals(expectedResponse.getUsers(), actualResponse.getUsers());
    }

    @Test
    void getUserInfoByHotelId() {
        UserInfoResponse expectedResponse = userInfoResponse;

        UserInfo userInfo = new UserInfo();
        userInfo.setUsers(modelMapper.map(expectedResponse.getUsers(), User.class));
        userInfo.setHotel(expectedResponse.getHotel());

        Mockito.when(userInfoRepository.findUserInfoByHotel(1L)).thenReturn(Optional.of(userInfo));

        UserInfoResponse actualResponse = userInfoService.getUserInfoByHotelId(1L);

        assertEquals(expectedResponse.getUsers(), actualResponse.getUsers());
    }

    @Test
    void addUserInfo() {
    }
}