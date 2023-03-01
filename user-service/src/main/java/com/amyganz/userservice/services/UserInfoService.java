package com.amyganz.userservice.services;


import com.amyganz.userservice.dtos.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserInfoService {
//    List<UserInfoResponse> getAllUser();
//    PaginateResponse getAllUserWithPagination(Pageable pageable);
    UserInfoResponse getUserInfoByUserId(Long userId);
    UserInfoResponse getUserInfoByHotelId(Long hotelId);
//    UserResponse getUserByEmail(String email);
    UserInfoResponse addUserInfo(UserInfoRequest userInfoRequest);
//    UserResponse updateUser(UserRequest userRequest, Long id);
//    void deleteUser(Long id);
}
