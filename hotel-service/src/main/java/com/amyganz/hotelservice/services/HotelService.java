package com.amyganz.hotelservice.services;

import com.amyganz.hotelservice.dtos.HotelRequest;
import com.amyganz.hotelservice.dtos.HotelResponse;
import com.amyganz.hotelservice.dtos.PaginateResponse;

import org.springframework.data.domain.Pageable;

public interface HotelService {
    PaginateResponse getAllHotel(Pageable pageable);
    PaginateResponse getHotelByName(Pageable pageable, String name);
    PaginateResponse getHotelByLocation(Pageable pageable, String location);
    PaginateResponse getHotelByClass(Pageable pageable, Integer cls);
    HotelResponse getHotelById(Long id);
    HotelResponse addHotel(HotelRequest userRequest);
    HotelResponse updateHotel(HotelRequest userRequest, Long id);
    void deleteHotel(Long id);
}
