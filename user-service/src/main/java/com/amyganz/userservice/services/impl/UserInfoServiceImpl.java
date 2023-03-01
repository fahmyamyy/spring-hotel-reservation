package com.amyganz.userservice.services.impl;

import com.amyganz.userservice.dtos.*;
import com.amyganz.userservice.entities.User;
import com.amyganz.userservice.entities.UserInfo;
import com.amyganz.userservice.exceptions.ServerException;
import com.amyganz.userservice.exceptions.user.UserAlreadyReceptionist;
import com.amyganz.userservice.exceptions.userinfo.UserInfoHotelAlreadyExists;
import com.amyganz.userservice.exceptions.userinfo.UserInfoHotelNotExists;
import com.amyganz.userservice.exceptions.userinfo.UserInfoNotExists;
import com.amyganz.userservice.exceptions.userinfo.UserInfoUserNotExists;
import com.amyganz.userservice.repositories.UserInfoRepository;
import com.amyganz.userservice.repositories.UserRepository;
import com.amyganz.userservice.services.UserInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
//@AllArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired

    private WebClient webClient;

    private UserInfoResponse convertToResponse(UserInfo userInfo) {return modelMapper.map(userInfo, UserInfoResponse.class);}


    @Override
    public UserInfoResponse getUserInfoByUserId(Long userId) {
        Optional<UserInfo> userOptional = userInfoRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserInfoNotExists();
        }
        return convertToResponse(userOptional.get());
    }

    @Override
    public UserInfoResponse getUserInfoByHotelId(Long hotelId) {
        Optional<UserInfo> userOptional = userInfoRepository.findUserInfoByHotel(hotelId);
        if (userOptional.isEmpty()) {
            throw new UserInfoNotExists();
        }
        return convertToResponse(userOptional.get());
    }

    @Override
    public UserInfoResponse addUserInfo(UserInfoRequest userInfoRequest) {
        Optional<User> userOptional = userRepository.findById(userInfoRequest.getUser());
        if (userOptional.isEmpty()) {
            throw new UserInfoUserNotExists();
        }

        try {
            ResponseDTO<HotelResponse> monoHotel = webClient.get()
                    .uri("localhost:8083/api/v1/hotel/id/" + userInfoRequest.getHotel())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(ResponseDTO.class)
                    .block();
        } catch (Exception e) {
            String[] code = e.getMessage().split(" ");
            if (code[0].equals("404")) {
                throw new UserInfoHotelNotExists();
            } else if (code[0].equals("Connection")) {
                throw new ServerException("Connection Refused");
            }
        }

        User user = userOptional.get();
        Optional<UserInfo> userInfo = userInfoRepository.findUserInfoByUsers(user);
        if (userInfo.isPresent()) {
            throw new UserAlreadyReceptionist();
        }

        Optional<UserInfo> userReceptionist = userInfoRepository.findUserInfoByHotel(userInfoRequest.getHotel());
        if (userReceptionist.isPresent()) {
            throw new UserInfoHotelAlreadyExists();
        }

        UserInfo saveUserInfo = new UserInfo(user.getId(), userInfoRequest.getHotel(), user);
        saveUserInfo.setId(null);
        userInfoRepository.save(saveUserInfo);

        user.setRole("receptionist");
        saveUserInfo.setUsers(user);
        return convertToResponse(saveUserInfo);
    }
}
