package com.amyganz.userservice.services;


import com.amyganz.userservice.dtos.PaginateResponse;
import com.amyganz.userservice.dtos.UserRequest;
import com.amyganz.userservice.dtos.UserResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUser();
    PaginateResponse getAllUserWithPagination(Pageable pageable);
    UserResponse getUserById(Long id);
    UserResponse getUserByUsername(String username);
    UserResponse getUserByEmail(String email);
    UserResponse addUser(UserRequest userRequest);
    UserResponse updateUser(UserRequest userRequest, Long id);
    void userSetAsReceptionist(Long id);
    void deleteUser(Long id);
}
