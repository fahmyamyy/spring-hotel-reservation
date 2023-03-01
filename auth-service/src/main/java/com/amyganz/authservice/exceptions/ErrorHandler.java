package com.amyganz.authservice.exceptions;


import com.amyganz.authservice.dtos.ResponseDTO;
import com.amyganz.authservice.exceptions.auth.*;
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
                        "[1G-999] General Error, Internal Server error"
                ),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // AUTH EXCEPTIONS
    @ExceptionHandler(value = UserUsernameAlreadyExists.class)
    public ResponseEntity<Object> handlerDuplicateUsername() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.CONFLICT,
                        "[1A-001] Creation failed, User username already exists"
                ),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = UserEmailAlreadyExists.class)
    public ResponseEntity<Object> handlerDuplicateEmail() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.CONFLICT,
                        "[1A-002] Creation failed, User email already exists"
                ),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = InvalidPassword.class)
    public ResponseEntity<Object> handlerInvalidPassword() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.BAD_REQUEST,
                        "[1A-003] Creation failed, User password invalid"
                ),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserUnderage.class)
    public ResponseEntity<Object> handlerUnderageUser() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.BAD_REQUEST,
                        "[1A-004] Creation failed, User is underage"
                ),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidCredentials.class)
    public ResponseEntity<Object> handlerCredentialsInvalid() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.BAD_REQUEST,
                        "[1A-005] Action failed. Invalid credentials"
                ),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidToken.class)
    public ResponseEntity<Object> handlerTokenInvalid() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.BAD_REQUEST,
                        "[1A-006] Action failed. Invalid token"
                ),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidOTP.class)
    public ResponseEntity<Object> handlerOTPInvalid() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.BAD_REQUEST,
                        "[1A-007] Action failed. Invalid OTP"
                ),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidOTPExpired.class)
    public ResponseEntity<Object> handlerExpiredOTPInvalid() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.BAD_REQUEST,
                        "[1A-008] Action failed. OTP expired"
                ),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserNotExists.class)
    public ResponseEntity<Object> handlerNotExistsUser() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.NOT_FOUND,
                        "[1A-009] Not found, User does not exists"
                ),
                HttpStatus.NOT_FOUND);
    }
}
