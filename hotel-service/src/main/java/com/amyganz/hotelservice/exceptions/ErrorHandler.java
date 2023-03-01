package com.amyganz.hotelservice.exceptions;

import com.amyganz.hotelservice.dtos.ResponseDTO;
import com.amyganz.hotelservice.exceptions.hotel.HotelAlreadyExists;
import com.amyganz.hotelservice.exceptions.hotel.HotelNameInvalid;
import com.amyganz.hotelservice.exceptions.hotel.HotelNotExists;
import com.amyganz.hotelservice.exceptions.room.RoomAlreadyExists;
import com.amyganz.hotelservice.exceptions.room.RoomHotelNotExists;
import com.amyganz.hotelservice.exceptions.room.RoomNotExists;
import com.amyganz.hotelservice.exceptions.room.RoomRoomTypeNotExists;
import com.amyganz.hotelservice.exceptions.roomtype.RoomTypeAlreadyExists;
import com.amyganz.hotelservice.exceptions.roomtype.RoomTypeHotelNotExist;
import com.amyganz.hotelservice.exceptions.roomtype.RoomTypeNameInvalid;
import com.amyganz.hotelservice.exceptions.roomtype.RoomTypeNotExists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;


@RestControllerAdvice

public class ErrorHandler {
//    private Map<String, String> buildResponse(String code, String message) {
//        Map<String, String> response = new HashMap<>();
//        response.put("code", code);
//        response.put("message", message);
//
//        return response;
//    }

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
                        "[3G-999] General Error, Internal Server error"
                ),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // HOTEL EXCEPTIONS
    @ExceptionHandler(value = HotelAlreadyExists.class)
    public ResponseEntity<Object> handlerDuplicateHotel() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.CONFLICT,
                        "[3H-001] Creation failed, Hotel name already exists"
                ),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = HotelNameInvalid.class)
    public ResponseEntity<Object> handlerNameInvalidHotel() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.BAD_REQUEST,
                        "[3H-002] Creation failed, Invalid Hotel name"
                ),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HotelNotExists.class)
    public ResponseEntity<Object> handlerNotFoundHotel() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.NOT_FOUND,
                        "[3H-003] Not found. Hotel does not exists"
                ),
                HttpStatus.NOT_FOUND);
    }

    // ROOM EXCEPTIONS
    @ExceptionHandler(value = RoomAlreadyExists.class)
    public ResponseEntity<Object> handlerDuplicateRoom() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.CONFLICT,
                        "[3R-001] Creation failed, Room already exists"
                ),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = RoomHotelNotExists.class)
    public ResponseEntity<Object> handlerHotelNotExistForRoom() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.BAD_REQUEST,
                        "[3R-002] Creation failed, Hotel does not exists"
                ),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RoomRoomTypeNotExists.class)
    public ResponseEntity<Object> handlerRoomtypeNotExistForRoom() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.BAD_REQUEST,
                        "[3R-003] Creation failed, Room Type does not exists"
                ),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RoomNotExists.class)
    public ResponseEntity<Object> handlerNotFoundRoom() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.NOT_FOUND,
                        "[3R-004] Not found. Room does not exists"
                ),
                HttpStatus.NOT_FOUND);

    }


    // ROOMTYPE EXCEPTIONS
    @ExceptionHandler(value = RoomTypeAlreadyExists.class)
    public ResponseEntity<Object> handlerDuplicateRoomType() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.CONFLICT,
                        "[3T-001] Creation failed, Room Type already exists"
                ),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = RoomTypeNameInvalid.class)
    public ResponseEntity<Object> handlerNameInvalidRoomType() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.BAD_REQUEST,
                        "[3T-002] Creation failed, Invalid Room Type name"
                ),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RoomTypeHotelNotExist.class)
    public ResponseEntity<Object> handlerHotelNotExistRoomType() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.BAD_REQUEST,
                        "[3T-003] Creation failed, Hotel does not exists"
                ),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RoomTypeNotExists.class)
    public ResponseEntity<Object> handlerNotFoundRoomType() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.NOT_FOUND,
                        "[3T-004] Not found. Room Type does not exists"
                ),
                HttpStatus.NOT_FOUND);
    }

}
