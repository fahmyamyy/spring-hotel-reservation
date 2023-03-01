package com.amyganz.userservice.exceptions;

import com.amyganz.userservice.dtos.ResponseDTO;
import com.amyganz.userservice.exceptions.user.*;
import com.amyganz.userservice.exceptions.userinfo.UserInfoHotelAlreadyExists;
import com.amyganz.userservice.exceptions.userinfo.UserInfoHotelNotExists;
import com.amyganz.userservice.exceptions.userinfo.UserInfoNotExists;
import com.amyganz.userservice.exceptions.userinfo.UserInfoUserNotExists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;


@RestControllerAdvice

public class ErrorHandler {
    private ResponseDTO buildResponse(HttpStatus httpStatus, String message) {
        return ResponseDTO.builder()
                .httpStatus(httpStatus)
                .status(false)
                .message(message)
                .data(Collections.emptyList())
                .build();
    }

    @ExceptionHandler(value = ServerException.class)
    public ResponseEntity<Object> handlerGeneralError() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "[2G-999] General Error, Internal Server error"
                ),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // USER EXCEPTIONS
    @ExceptionHandler(value = UserUsernameAlreadyExists.class)
    public ResponseEntity<Object> handlerDuplicateUsername() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.CONFLICT,
                        "[3U-001] Creation failed, User username already exists"
                ),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = UserEmailAlreadyExists.class)
    public ResponseEntity<Object> handlerDuplicateEmail() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.CONFLICT,
                        "[3U-002] Creation failed, User email already exists"
                ),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = UserPasswordNotValid.class)
    public ResponseEntity<Object> handlerInvalidPassword() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.BAD_REQUEST,
                        "[3U-003] Creation failed, User password invalid"
                ),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserUnderage.class)
    public ResponseEntity<Object> handlerUnderageUser() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.BAD_REQUEST,
                        "[3U-004] Creation failed, User is underage"
                ),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserAlreadyReceptionist.class)
    public ResponseEntity<Object> handlerAlreadyReceptionistUser() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.CONFLICT,
                        "[3U-005] Action failed, User role already \"receptionist\""
                ),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = UserNotExists.class)
    public ResponseEntity<Object> handlerNotExistsUser() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.NOT_FOUND,
                        "[3U-006] Not found. User does not exists"
                ),
                HttpStatus.NOT_FOUND);
    }

    // USERINFO EXCEPTIONS
    @ExceptionHandler(value = UserInfoHotelAlreadyExists.class)
    public ResponseEntity<Object> handlerDuplicateHotelUserInfo() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.CONFLICT,
                        "[3I-001] Creation failed, Hotel already has receptionist"
                ),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = UserInfoUserNotExists.class)
    public ResponseEntity<Object> handlerNotFoundUserUserInfo() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.BAD_REQUEST,
                        "[3I-002] Creation failed, User does not exists"
                ),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserInfoHotelNotExists.class)
    public ResponseEntity<Object> handlerNotFoundHotelUserInfo() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.BAD_REQUEST,
                        "[3I-003] Creation failed, Hotel does not exists"
                ),
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = UserInfoNotExists.class)
    public ResponseEntity<Object> handlerNotFoundUserInfo() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.NOT_FOUND,
                        "[3I-004] Not found, User Info does not exists"
                ),
                HttpStatus.NOT_FOUND);
    }
}
