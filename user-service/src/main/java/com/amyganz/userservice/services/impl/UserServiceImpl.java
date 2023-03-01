package com.amyganz.userservice.services.impl;

import com.amyganz.userservice.dtos.PaginateResponse;
import com.amyganz.userservice.dtos.UserRequest;
import com.amyganz.userservice.dtos.UserResponse;
import com.amyganz.userservice.entities.User;
import com.amyganz.userservice.exceptions.user.*;
import com.amyganz.userservice.exceptions.userinfo.UserInfoUserNotExists;
import com.amyganz.userservice.helpers.PasswordValidator;
import com.amyganz.userservice.repositories.UserRepository;
import com.amyganz.userservice.services.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

@Service
@Transactional
//@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder encoder;

    private UserResponse convertToResponse(User user) {return modelMapper.map(user, UserResponse.class);}

    @Transactional
    @Override
    public UserResponse addUser(UserRequest userRequest) {
        Optional<User> usernameOptional = userRepository.findByUsername(userRequest.getUsername());
        if (usernameOptional.isPresent()) {
            throw new UserUsernameAlreadyExists();
        }

        Optional<User> emailOptional = userRepository.findByEmail(userRequest.getEmail());
        if (emailOptional.isPresent()) {
            throw new UserEmailAlreadyExists();
        }

        LocalDate birthdateLocal = userRequest.getDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period period = Period.between(birthdateLocal, LocalDate.now());
        int years = period.getYears();

        if (years < 18) {
            throw new UserUnderage();
        }

        User user = modelMapper.map(userRequest, User.class);
        if (!PasswordValidator.isValid(user.getPassword())) {
            throw new UserPasswordNotValid();

        }
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return convertToResponse(user);
    }

    @Override
    @Cacheable
    public List<UserResponse> getAllUser() {
        List<User> userList = userRepository.findAll();
        List<UserResponse> userResponseList = new ArrayList<>();
        for (User user : userList) {
            UserResponse userResponse = modelMapper.map(user, UserResponse.class);
            userResponseList.add(userResponse);
        }
        return userResponseList;
    }

    @Override
    public PaginateResponse getAllUserWithPagination(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        List<UserRequest> userRequestList = new ArrayList<>();
        for (User user : users.getContent()) {
            UserRequest userRequest = modelMapper.map(user, UserRequest.class);
            userRequestList.add(userRequest);
        }
        return PaginateResponse.builder()
                .items(Collections.singletonList(userRequestList))
                .totalItems(users.getTotalElements())
                .totalPages(users.getTotalPages())
                .currentPage(users.getNumber()).build();
    }
    @Override
    public UserResponse getUserById(Long id) {
//        Optional<User> userOptional = userRepository.findById(id);
//        if (userOptional.isPresent()) {
//            UserResponse userResponse = modelMapper.map(userOptional.get(), UserResponse.class);
//            log.info("Getting User with id : {} from databases", id);
//            return userResponse;
//        }
//        log.error("User with id : {} not found!", id, new UserNotFoundException("user not found"));
//        throw new UserNotFoundException("UserNotFound");

        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new UserNotExists();
        }
        return convertToResponse(userOptional.get());
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UserNotExists();
        }
        return convertToResponse(userOptional.get());
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            throw new UserNotExists();
        }
        return convertToResponse(userOptional.get());
    }
//    @Override
//    @Transactional()
//    @CacheEvict(value = "users", allEntries = true)
//    public void addUser(UserDTO userDTO) {
//        User user = modelMapper.map(userDTO, User.class);
//        userRepository.save(user);
//        log.info("Success add user : {}", userDTO.toString());
//    }
    @Override
    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public UserResponse updateUser(UserRequest userRequest, Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            throw new UserNotExists();
        }

        User user = optionalUser.get();
        modelMapper.map(userRequest, user);
        user.setPassword(encoder.encode(user.getPassword()));
        User updatedUser = userRepository.save(user);
        return convertToResponse(updatedUser);
    }

    @Override
    public void userSetAsReceptionist(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            throw new UserInfoUserNotExists();
        } else if (!optionalUser.get().getRole().equals("receptionist")) {
            throw new UserAlreadyReceptionist();
        }

        User user = optionalUser.get();
        user.setRole("receptionist");
        userRepository.save(user);
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public void deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            throw new UserNotExists();
        }
        User user = optionalUser.get();
        user.setStatus(false);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }
}
