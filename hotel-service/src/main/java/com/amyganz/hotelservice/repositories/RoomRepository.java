package com.amyganz.hotelservice.repositories;

import com.amyganz.hotelservice.entities.Hotel;
import com.amyganz.hotelservice.entities.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomNumberAndHotel(Integer roomNumber, Hotel hotel);
    Page<Room> findRoomsByHotelIdEqualsAndDeletedAtIsNull(Pageable pageable, Long id);
    Page<Room> findRoomsByDeletedAtIsNull(Pageable pageable);
    Page<Room> findRoomsByRoomTypeIdEqualsAndDeletedAtIsNull(Pageable pageable, Long id);
    Page<Room> findRoomsByAvailableIsTrueAndDeletedAtIsNull(Pageable pageable);
    Page<Room> findRoomsByHotelIdEqualsAndRoomTypeIdEquals(Pageable pageable, Long hotelId, Long typeId);
}
