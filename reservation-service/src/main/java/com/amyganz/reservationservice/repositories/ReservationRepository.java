package com.amyganz.reservationservice.repositories;

import com.amyganz.reservationservice.entities.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Page<Reservation> findReservationsByUserEquals(Pageable pageable, Integer user);
    Page<Reservation> findReservationsByHotelEquals(Pageable pageable, Integer hotelId);
    Optional<Reservation> findReservationByUserEqualsAndRoomEquals(Integer user, Integer room);
}
