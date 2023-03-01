package com.amyganz.hotelservice.repositories;

import com.amyganz.hotelservice.entities.Room;
import com.amyganz.hotelservice.entities.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
    Page<RoomType> findRoomTypesByDeletedAtIsNull(Pageable pageable);
    Page<RoomType> findRoomTypesByHotelIdEqualsAndDeletedAtIsNull(Pageable pageable, Long id);
    Page<RoomType> findRoomTypesByChargeLessThanAndDeletedAtIsNull(Pageable pageable, Integer charge);
}
