package com.amyganz.hotelservice.services.impl;

import com.amyganz.hotelservice.dtos.HotelRequest;
import com.amyganz.hotelservice.dtos.HotelResponse;
import com.amyganz.hotelservice.dtos.PaginateResponse;
import com.amyganz.hotelservice.entities.Hotel;
import com.amyganz.hotelservice.exceptions.hotel.HotelAlreadyExists;
import com.amyganz.hotelservice.exceptions.hotel.HotelNameInvalid;
import com.amyganz.hotelservice.exceptions.hotel.HotelNotExists;
import com.amyganz.hotelservice.helpers.AppHelper;
import com.amyganz.hotelservice.repositories.HotelRepository;
import com.amyganz.hotelservice.services.HotelService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
//@AllArgsConstructor
public class HotelImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ModelMapper modelMapper;

    private HotelResponse convertToResponse(Hotel hotel) {return modelMapper.map(hotel, HotelResponse.class);}
    private PaginateResponse getPaginateResponse(Page<Hotel> hotels) {
        List<HotelResponse> hotelList = new ArrayList<>();
        for (Hotel hotel : hotels.getContent()) {
            HotelResponse hotelDTO = modelMapper.map(hotel, HotelResponse.class);
            hotelList.add(hotelDTO);
        }
        return PaginateResponse.builder()
                .items(Collections.singletonList(hotelList))
                .totalItems(hotels.getTotalElements())
                .totalPages(hotels.getTotalPages())
                .currentPage(hotels.getNumber()).build();
    }

    @Override
    @Cacheable(value = "hotel")
    public PaginateResponse getAllHotel(Pageable pageable) {
        Page<Hotel> hotels = hotelRepository.findHotelsByDeletedAtIsNull(pageable);
        return getPaginateResponse(hotels);
    }

    @Override
    @Cacheable(value = "hotel")
    public PaginateResponse getHotelByLocation(Pageable pageable, String location) {
        Page<Hotel> hotels = hotelRepository.findByLocationContainsAndDeletedAtIsNull(pageable, location);
        return getPaginateResponse(hotels);
    }

    @Override
    @Cacheable(value = "hotel")
    public PaginateResponse getHotelByClass(Pageable pageable, Integer cls) {
        Page<Hotel> hotels = hotelRepository.findByClsEqualsAndDeletedAtIsNull(pageable, cls);
        return getPaginateResponse(hotels);
    }

    @Override
    @Cacheable(value = "hotel")
    public PaginateResponse getHotelByName(Pageable pageable, String name) {
        Page<Hotel> hotels = hotelRepository.findByNameContainsAndDeletedAtIsNull(pageable, name);
        return getPaginateResponse(hotels);
    }

    @Override
    @Cacheable(value = "hotel")
    public HotelResponse getHotelById(Long id) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(id);
        if (hotelOptional.isEmpty()) {
            throw new HotelNotExists();
        }
        return convertToResponse(hotelOptional.get());
    }

    @Override
    @CacheEvict(value = "hotel", allEntries = true)
    public HotelResponse addHotel(HotelRequest hotelRequest) {
        Hotel hotel = modelMapper.map(hotelRequest, Hotel.class);
        if (AppHelper.hasSymbols(hotel.getName())) {
            throw new HotelNameInvalid();
        }

        Optional<Hotel> hotelOptional = hotelRepository.findByNameEqualsAndDeletedAtIsNull(hotel.getName());
        if (hotelOptional.isPresent()) {
            throw new HotelAlreadyExists();
        }

        hotelRepository.save(hotel);
        return convertToResponse(hotel);
    }

    @Override
    @CacheEvict(value = "hotel", allEntries = true)
    public HotelResponse updateHotel(HotelRequest hotelRequest, Long id) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);

        if (optionalHotel.isEmpty()) {
            throw new HotelNotExists();
        }

        Hotel hotel = optionalHotel.get();
        modelMapper.map(hotelRequest, hotel);
        Hotel updatedHotel = hotelRepository.save(hotel);
        return convertToResponse(updatedHotel);
    }

    @Override
    @CacheEvict(value = "hotel", allEntries = true)
    public void deleteHotel(Long id) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);

        if (optionalHotel.isEmpty()) {
            throw new HotelNotExists();
        }

        Hotel hotel = optionalHotel.get();
        hotel.setDeletedAt(LocalDateTime.now());
        hotel.setStatus(false);
        hotelRepository.save(hotel);
    }
}
