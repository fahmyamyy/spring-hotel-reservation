package com.amyganz.reservationservice.controllers;

import com.amyganz.reservationservice.dtos.PaginateResponse;
import com.amyganz.reservationservice.dtos.ReservationRequest;
import com.amyganz.reservationservice.dtos.ReservationResponse;
import com.amyganz.reservationservice.dtos.ResponseDTO;
import com.amyganz.reservationservice.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("api/v1/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping
    @Cacheable("reservations")
    public ResponseDTO<PaginateResponse> getAllReservation(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "5") int limit
    ) {
        int offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit);

        PaginateResponse paginateResponse = reservationService.getAllReservation(pageable);
        return ResponseDTO.<PaginateResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully fetch Reservation datas")
                .data(paginateResponse).build();
    }

//    @GetMapping("name")
//    @Cacheable("reservations")
//    public ResponseDTO<PaginateResponse> getReservationByName(
//            @RequestParam(name = "page", defaultValue = "1") int page,
//            @RequestParam(name = "limit", defaultValue = "5") int limit,
//            @RequestParam(name = "name") String name
//    ) {
//        int offset = page - 1;
//        Pageable pageable = PageRequest.of(offset, limit);
//
//        PaginateResponse paginateResponse = reservationService.getReservationByName(pageable, name);
////        return new ResponseEntity<ResponseDTO<PaginateResponse>>(ResponseDTO
////                .<PaginateResponse>builder()
////                .httpStatus(HttpStatus.OK)
////                .status(true)
////                .message("Successfully fetch Reservation datas with name contains: " + name)
////                .data(paginateResponse).build(), HttpStatus.OK);
//        return ResponseDTO.<PaginateResponse>builder()
//                .httpStatus(HttpStatus.OK)
//                .status(true)
//                .message("Successfully fetch Reservation datas with name contains: " + name)
//                .data(paginateResponse).build();
//    }

//    @GetMapping("location")
//    @Cacheable("reservations")
//    public ResponseDTO<PaginateResponse> getReservationByLocation(
//            @RequestParam(name = "page", defaultValue = "1") int page,
//            @RequestParam(name = "limit", defaultValue = "5") int limit,
//            @RequestParam(name = "location") String location
//    ) {
//        int offset = page - 1;
//        Pageable pageable = PageRequest.of(offset, limit);
//
//        PaginateResponse paginateResponse = reservationService.getReservationByLocation(pageable, location);
////        return new ResponseEntity<ResponseDTO<PaginateResponse>>(ResponseDTO
////                .<PaginateResponse>builder()
////                .httpStatus(HttpStatus.OK)
////                .status(true)
////                .message("Successfully fetch Reservation datas with location contains: " + location)
////                .data(paginateResponse).build(), HttpStatus.OK);
//        return ResponseDTO.<PaginateResponse>builder()
//                .httpStatus(HttpStatus.OK)
//                .status(true)
//                .message("Successfully fetch Reservation datas with location contains: " + location)
//                .data(paginateResponse).build();
//    }

//    @GetMapping("class")
//    @Cacheable("reservations")
//    public ResponseDTO<PaginateResponse> getReservationByClass(
//            @RequestParam(name = "page", defaultValue = "1") int page,
//            @RequestParam(name = "limit", defaultValue = "5") int limit,
//            @RequestParam(name = "class") Integer cls
//    ) {
//        int offset = page - 1;
//        Pageable pageable = PageRequest.of(offset, limit);
//
//        PaginateResponse paginateResponse = reservationService.getReservationByClass(pageable, cls);
//        return ResponseDTO.<com.amyganz.reservationservice.dtos.PaginateResponse>builder()
//                .httpStatus(HttpStatus.OK)
//                .status(true)
//                .message("Successfully fetch Reservation datas with class: " + cls)
//                .data(paginateResponse).build();
//    }

//    @GetMapping("user/{id}")
//    @Cacheable("reservations")
//    public ResponseDTO<ReservationResponse> getReservationByUserId(@PathVariable Integer id) {
//        ReservationResponse reservationResponse = reservationService.getReservationsByUserEquals(id);
//        return ResponseDTO.<ReservationResponse>builder()
//                .httpStatus(HttpStatus.OK)
//                .status(true)
//                .message("Successfully fetch Reservation data with User Id: " + id)
//                .data(reservationResponse).build();
//    }
    @GetMapping("user/{id}")
    @Cacheable("reservations")
    public ResponseDTO<PaginateResponse> getReservationByUserId(
            @PathVariable Integer id,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "5") int limit
    ) {
        int offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit);

        PaginateResponse paginateResponse = reservationService.getReservationsByUserEquals(pageable, id);
        return ResponseDTO.<PaginateResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully fetch Reservation datas with User id: " + id)
                .data(paginateResponse).build();
    }

    @GetMapping("hotel/{id}")
    @Cacheable("reservations")
    public ResponseDTO<PaginateResponse> getReservationByHotelId(
            @PathVariable Integer id,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "5") int limit
    ) {
        int offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit);

        PaginateResponse paginateResponse = reservationService.getReservationsByHotelEquals(pageable, id);
        return ResponseDTO.<PaginateResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully fetch Reservation datas with Hotel id: " + id)
                .data(paginateResponse).build();
    }

    @GetMapping("id/{id}")
    @Cacheable("reservations")
    public ResponseDTO<ReservationResponse> getReservationById(@PathVariable Long id) {
        ReservationResponse reservationResponse = reservationService.getReservationById(id);
        return ResponseDTO.<ReservationResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully fetch Reservation data with id: " + id)
                .data(reservationResponse).build();
    }


    @PostMapping
    @CacheEvict(value = "reservations", allEntries = true)
    public ResponseEntity<ResponseDTO> addReservation(@RequestBody ReservationRequest reservationRequest) {
        reservationService.addReservation(reservationRequest);
        return new ResponseEntity<ResponseDTO>(ResponseDTO.builder()
                .httpStatus(HttpStatus.CREATED)
                .status(true)
                .message("Successfully creating new Reservation")
                .data(reservationRequest).build(), HttpStatus.CREATED);
    }

//    @PutMapping("/{id}")
//    @CacheEvict(value = "reservations", allEntries = true)
//    public ResponseEntity<ResponseDTO<ReservationResponse>> updateReservation(@RequestBody ReservationRequest reservationRequest, @PathVariable Long id) {
//        ReservationResponse reservationResponse = reservationService.   updateReservation(reservationRequest, id);
//        return new ResponseEntity<ResponseDTO<ReservationResponse>>(ResponseDTO.<ReservationResponse>builder()
//                .httpStatus(HttpStatus.OK)
//                .status(true)
//                .message("Successfully updating Reservation with id: " + id)
//                .data(reservationResponse)
//                .build(), HttpStatus.OK);
//    }

    @PutMapping("cancel/{id}")
    @CacheEvict(value = "reservations", allEntries = true)
    public ResponseEntity<ResponseDTO> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return new ResponseEntity<ResponseDTO>(ResponseDTO.builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully canceling Reservation with id: " + id)
                .data(Collections.emptyList())
                .build(), HttpStatus.OK);
    }

    @PutMapping("paid/{id}")
    @CacheEvict(value = "reservations", allEntries = true)
    public ResponseEntity<ResponseDTO> paidReservation(
            @PathVariable Long id,
            @RequestParam(name = "paymentId") Long paymentId
    ) {
        reservationService.paidReservation(id, paymentId);
        return new ResponseEntity<ResponseDTO>(ResponseDTO.builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully paid Reservation with id: " + id)
                .data(Collections.emptyList())
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "reservations", allEntries = true)
    public ResponseEntity<ResponseDTO> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return new ResponseEntity<ResponseDTO>(ResponseDTO.builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully deleting Reservation with id: " + id)
                .data(Collections.emptyList())
                .build(), HttpStatus.OK);
    }
}
