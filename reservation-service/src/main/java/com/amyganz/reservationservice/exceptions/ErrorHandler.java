package com.amyganz.reservationservice.exceptions;


import com.amyganz.reservationservice.dtos.ResponseDTO;
import com.amyganz.reservationservice.exceptions.invoice.InvoiceAlreadyExists;
import com.amyganz.reservationservice.exceptions.invoice.InvoiceNotFound;

import com.amyganz.reservationservice.exceptions.reservation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
                        "[4G-999] General Error, Internal Server error"
                ),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // RESERVATION EXCEPTIONS
    @ExceptionHandler(value = ReservationAlreadyExists.class)
    public ResponseEntity<Object> handlerAlreadyExistReservation() {
//        return new ResponseEntity<>(
//                buildResponse("4R-001", "Creation failed. Reservation already exists"),
//                HttpStatus.CONFLICT);
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.CONFLICT,
                        "[4R-001] Creation failed. Reservation already exists"
                ),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = ReservationNotFoundStatus.class)
    public ResponseEntity<Object> handlerNotFoundReservationReservationStatus() {
//        return new ResponseEntity<>(
//                buildResponse("4R-002", "Creation failed. Reservation Status does not exists"),
//                HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.BAD_REQUEST,
                        "[4R-002] Creation failed. Reservation Status does not exists"
                ),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ReservationNotFoundPayment.class)
    public ResponseEntity<Object> handlerNotFoundReservationPayment() {
//        return new ResponseEntity<>(
//                buildResponse("4R-003", "Creation failed. Payment Method does not exists"),
//                HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.BAD_REQUEST,
                        "[4R-003] Creation failed. Payment Method does not exists"
                ),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ReservationNotFoundHotel.class)
    public ResponseEntity<Object> handlerNotFoundReservationHotel() {
//        return new ResponseEntity<>(
//                buildResponse("4R-004", "Creation failed. Hotel does not exists"),
//                HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.BAD_REQUEST,
                        "[4R-004] Creation failed. Hotel does not exists"
                ),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ReservationNotFoundUser.class)
    public ResponseEntity<Object> handlerNotFoundReservationUser() {
//        return new ResponseEntity<>(
//                buildResponse("4R-005", "Creation failed. User does not exists"),
//                HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.BAD_REQUEST,
                        "[4R-005] Creation failed. User does not exists"
                ),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ReservationNotFoundRoom.class)
    public ResponseEntity<Object> handlerNotFoundReservationRoom() {
//        return new ResponseEntity<>(
//                buildResponse("4R-005", "Creation failed. User does not exists"),
//                HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.BAD_REQUEST,
                        "[4R-006] Creation failed. Room does not exists"
                ),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ReservationRoomNotAvailable.class)
    public ResponseEntity<Object> handlerNotAvailableReservationRoom() {
//        return new ResponseEntity<>(
//                buildResponse("4R-005", "Creation failed. User does not exists"),
//                HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.BAD_REQUEST,
                        "[4R-007] Creation failed. Room does not available"
                ),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ReservationAlreadyPaid.class)
    public ResponseEntity<Object> handlerAlreadyPaidReservation() {
//        return new ResponseEntity<>(
//                buildResponse("4R-006", "Action failed. Reservation already paid"),
//                HttpStatus.CONFLICT);
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.CONFLICT,
                        "[4R-008] Action failed. Reservation already paid"
                ),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = ReservationAlreadyCanceled.class)
    public ResponseEntity<Object> handlerAlreadyCanceledReservation() {
//        return new ResponseEntity<>(
//                buildResponse("4R-007", "Action failed. Reservation already canceled"),
//                HttpStatus.CONFLICT);
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.CONFLICT,
                        "[4R-009] Action failed. Reservation already canceled"
                ),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = ReservationNotFound.class)
    public ResponseEntity<Object> handlerNotFoundReservation() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.NOT_FOUND,
                        "[4R-010] Not found. Reservation does not exists"
                ),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserNotExists.class)
    public ResponseEntity<Object> handlerNotFoundUser() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.NOT_FOUND,
                        "[4R-011] Not found. User does not exists"
                ),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = HotelNotExists.class)
    public ResponseEntity<Object> handlerNotFoundHotel() {
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.NOT_FOUND,
                        "[4R-011] Not found. Hotel does not exists"
                ),
                HttpStatus.NOT_FOUND);
    }


    // INVOICE EXCEPTIONS
    @ExceptionHandler(value = InvoiceAlreadyExists.class)
    public ResponseEntity<Object> handlerAlreadyExistInvoice() {
//        return new ResponseEntity<>(
//                buildResponse("4R-001", "Creation failed. Invoice already exists"),
//                HttpStatus.CONFLICT);
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.CONFLICT,
                        "[4R-001] Creation failed. Invoice already exists"
                ),
                HttpStatus.CONFLICT);
    }
    @ExceptionHandler(value = InvoiceNotFound.class)
    public ResponseEntity<Object> handlerNotFoundInvoice() {
//        return new ResponseEntity<>(
//                buildResponse("4R-002", "Not found. Invoice does not exists"),
//                HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(
                buildResponse(
                        HttpStatus.NOT_FOUND,
                        "[4R-002] Not found. Invoice does not exists"
                ),
                HttpStatus.NOT_FOUND);
    }

//    // RESERVATION STATUS EXCEPTION
//    @ExceptionHandler(value = ReservationStatusNotFound.class)
//    public ResponseEntity<Object> handlerNotFoundReservationStatus() {
//        return new ResponseEntity<>(
//                buildResponse("4S-001", "Not found. Reservation Status does not exists"),
//                HttpStatus.NOT_FOUND);
//    }
//
//    // PAYMENT METHOD EXCEPTION
//    @ExceptionHandler(value = PaymentMethodNotFound.class)
//    public ResponseEntity<Object> handlerNotFoundPaymentMethod() {
//        return new ResponseEntity<>(
//                buildResponse("4P-001", "Not found. Payment Method does not exists"),
//                HttpStatus.NOT_FOUND);
//    }
}
