package com.amyganz.hotelservice.services;

import com.amyganz.hotelservice.dtos.RoomTypeRequest;
import com.amyganz.hotelservice.dtos.RoomTypeResponse;
import com.amyganz.hotelservice.dtos.PaginateResponse;
import com.amyganz.hotelservice.dtos.RoomTypeShortResponse;
import org.springframework.data.domain.Pageable;

public interface RoomTypeService {
    PaginateResponse getAllRoomTypes(Pageable pageable);
    PaginateResponse getRoomTypeByHotelId(Pageable pageable, Long id);
    PaginateResponse getRoomTypeChargeLessThan(Pageable pageable, Integer charge);
    RoomTypeResponse getRoomTypeById(Long id);
    RoomTypeShortResponse addRoomType(RoomTypeRequest roomTypeRequest);
    RoomTypeResponse updateRoomType(RoomTypeRequest roomTypeRequest, Long id);
    void deleteRoomType(Long id);
}