package com.amyganz.userservice.controllers;

import com.amyganz.userservice.dtos.*;
import com.amyganz.userservice.services.UserInfoService;
import com.amyganz.userservice.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/v1/user/userinfo")
@Slf4j
@AllArgsConstructor
public class UserInfoController {

    private UserInfoService userInfoService;
    private UserService userService;

//    @GetMapping
//    public ResponseEntity<ResponseDTO<List<UserInfoResponse>>> getAllUser(){
//        List<UserInfoResponse> userList = userInfoService.getAllUser();
//        return new ResponseEntity<ResponseDTO<List<UserResponse>>>(ResponseDTO
//                .<List<UserResponse>>builder()
//                .httpStatus(HttpStatus.OK)
//                .status(true)
//                .message("Successfully fetch all Users")
//                .data(userList).build(), HttpStatus.OK);
//    }

//    @GetMapping("/pagination")
//    public ResponseEntity<ResponseDTO<PaginateResponse>> getAllUsersWithPagination(
//            @RequestParam(name = "page", defaultValue = "1") int page,
//            @RequestParam(name = "limit", defaultValue = "5") int limit
//    ) {
//        int offset = page - 1;
//        Pageable pageable = PageRequest.of(offset, limit);
//
//        PaginateResponse paginateResponse = userService.getAllUserWithPagination(pageable);
//        return new ResponseEntity<ResponseDTO<PaginateResponse>>(ResponseDTO
//                .<PaginateResponse>builder()
//                .httpStatus(HttpStatus.OK)
//                .status(true)
//                .message("Successfully fetch all Users")
//                .data(paginateResponse).build(), HttpStatus.OK);
//    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseDTO<UserInfoResponse>> getUserInfoByUserId(@PathVariable Long id) {
        UserInfoResponse userResponse = userInfoService.getUserInfoByUserId(id);
        return new ResponseEntity<ResponseDTO<UserInfoResponse>>(ResponseDTO.<UserInfoResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Found UserInfo with UserId: " + id)
                .data(userResponse).build(), HttpStatus.OK);
    }

    @GetMapping("hotel/{id}")
    public ResponseEntity<ResponseDTO<UserInfoResponse>> getUserInfoByHotelId(@PathVariable Long id) {
        UserInfoResponse userResponse = userInfoService.getUserInfoByHotelId(id);
        return new ResponseEntity<ResponseDTO<UserInfoResponse>>(ResponseDTO.<UserInfoResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Found UserInfo with HotelId: " + id)
                .data(userResponse).build(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addUser(@RequestBody UserInfoRequest userInfoRequest) {
        UserInfoResponse userInfoResponse = userInfoService.addUserInfo(userInfoRequest);
        userService.userSetAsReceptionist(userInfoRequest.getUser());
        return new ResponseEntity<ResponseDTO>(ResponseDTO.builder()
                .httpStatus(HttpStatus.CREATED)
                .status(true)
                .message("Successfully creating new UserInfo")
                .data(userInfoResponse).build(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<UserResponse>> updateUser(@RequestBody UserRequest userRequest, @PathVariable Long id) {
        UserResponse userResponse = userService.updateUser(userRequest, id);
        return new ResponseEntity<ResponseDTO<UserResponse>>(ResponseDTO.<UserResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully updating User with id: " + id)
                .data(userResponse)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<ResponseDTO>(ResponseDTO.builder()
                .httpStatus(HttpStatus.ACCEPTED)
                .status(true)
                .message("Successfully deleting User with id: " + id)
                .data(Collections.emptyList())
                .build(), HttpStatus.ACCEPTED);
    }
}
