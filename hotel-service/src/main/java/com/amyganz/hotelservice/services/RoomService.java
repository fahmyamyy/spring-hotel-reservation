package com.amyganz.hotelservice.services;

import com.amyganz.hotelservice.dtos.*;
import org.springframework.data.domain.Pageable;

public interface RoomService {
    PaginateResponse getAllRoom(Pageable pageable);
    PaginateResponse getRoomByAvailability(Pageable pageable);
    PaginateResponse getRoomByHotelId(Pageable pageable, Long id);
    PaginateResponse getRoomByRoomType(Pageable pageable, Long typeId);
    PaginateResponse getRoomByHotelAndRoomType(Pageable pageable, Long HotelId, Long typeId);
    RoomResponse getRoomById(Long id);
    RoomShortResponse addRoom(RoomRequest roomRequest);
    RoomResponse updateRoom(RoomRequest roomRequest, Long id);
    void unavailablingRoom(Long id);
    void deleteRoom(Long id);
}
