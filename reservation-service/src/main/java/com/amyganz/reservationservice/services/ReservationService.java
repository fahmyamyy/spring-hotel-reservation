package com.amyganz.reservationservice.services;

import com.amyganz.reservationservice.dtos.PaginateResponse;
import com.amyganz.reservationservice.dtos.ReservationRequest;
import com.amyganz.reservationservice.dtos.ReservationResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface ReservationService {
    PaginateResponse getAllReservation(Pageable pageable);
    PaginateResponse getReservationsByUserEquals(Pageable pageable, Integer id);
    PaginateResponse getReservationsByHotelEquals(Pageable pageable, Integer hotelId);

//    PaginateResponse getReservationByLocation(Pageable pageable, String location);
//    PaginateResponse getReservationByClass(Pageable pageable, Integer cls);
    ReservationResponse getReservationById(Long id);
    ReservationRequest addReservation(ReservationRequest reservationRequest);
    ReservationResponse updateReservation(ReservationRequest reservationRequest, Long id);
    void cancelReservation(Long id);
    void paidReservation(Long reservedId, Long paymentId);

    void deleteReservation(Long id);
}
