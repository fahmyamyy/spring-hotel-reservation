package com.amyganz.userservice.controllers;

import com.amyganz.userservice.dtos.*;
import com.amyganz.userservice.dtos.UserResponse;
import com.amyganz.userservice.services.UserService;
//import io.swagger.annotations.ApiOperation;
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
@RequestMapping("api/v1/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
//    @ApiOperation(value = "Get all Users", response = UserResponse.class)
    @GetMapping
    public ResponseEntity<ResponseDTO<List<UserResponse>>> getAllUser(){
        List<UserResponse> userList = userService.getAllUser();
        return new ResponseEntity<ResponseDTO<List<UserResponse>>>(ResponseDTO
                .<List<UserResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully fetch all Users")
                .data(userList).build(), HttpStatus.OK);
    }

    @GetMapping("/pagination")
    public ResponseEntity<ResponseDTO<PaginateResponse>> getAllUsersWithPagination(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "5") int limit
    ) {
        int offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit);

        PaginateResponse paginateResponse = userService.getAllUserWithPagination(pageable);
        return new ResponseEntity<ResponseDTO<PaginateResponse>>(ResponseDTO
                .<PaginateResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully fetch all Users")
                .data(paginateResponse).build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<UserResponse>> getUserById(@PathVariable Long id) {
        UserResponse userResponse = userService.getUserById(id);
        return new ResponseEntity<ResponseDTO<UserResponse>>(ResponseDTO.<UserResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Found User with id: " + id)
                .data(userResponse).build(), HttpStatus.OK);
    }

    @GetMapping("username/{username}")
    public ResponseEntity<ResponseDTO<UserResponse>> getUserByUsername(@PathVariable String username) {
        UserResponse userRequest = userService.getUserByUsername(username);
        return new ResponseEntity<ResponseDTO<UserResponse>>(ResponseDTO.<UserResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Found User with username: " + username)
                .data(userRequest).build(), HttpStatus.OK);
    }

    @GetMapping("email/{email}")
    public ResponseEntity<ResponseDTO<UserResponse>> getUserByEmail(@PathVariable String email) {
        UserResponse userRequest = userService.getUserByEmail(email);
        return new ResponseEntity<ResponseDTO<UserResponse>>(ResponseDTO.<UserResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Found User with email: " + email)
                .data(userRequest).build(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.addUser(userRequest);
        return new ResponseEntity<ResponseDTO>(ResponseDTO.builder()
                .httpStatus(HttpStatus.CREATED)
                .status(true)
                .message("Successfully creating new User")
                .data(userResponse).build(), HttpStatus.CREATED);
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
