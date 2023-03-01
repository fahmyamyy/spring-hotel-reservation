package com.amyganz.hotelservice.services.impl;

import com.amyganz.hotelservice.dtos.*;
import com.amyganz.hotelservice.entities.Hotel;
import com.amyganz.hotelservice.entities.Room;
import com.amyganz.hotelservice.entities.RoomType;
import com.amyganz.hotelservice.exceptions.roomtype.RoomTypeHotelNotExist;
import com.amyganz.hotelservice.exceptions.roomtype.RoomTypeNameInvalid;
import com.amyganz.hotelservice.exceptions.roomtype.RoomTypeNotExists;
import com.amyganz.hotelservice.helpers.AppHelper;
import com.amyganz.hotelservice.repositories.HotelRepository;
import com.amyganz.hotelservice.repositories.RoomTypeRepository;
import com.amyganz.hotelservice.services.RoomTypeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoomTypeImpl implements RoomTypeService {
    private final RoomTypeRepository roomTypeRepository;
    private final HotelRepository hotelRepository;
    private ModelMapper modelMapper;

    private RoomTypeResponse convertToResponse(RoomType roomType) {return modelMapper.map(roomType, RoomTypeResponse.class);}
    private RoomTypeShortResponse convertToShortResponse(RoomType roomType) {return modelMapper.map(roomType, RoomTypeShortResponse.class);}

    private PaginateResponse getPaginateResponse(Page<RoomType> roomTypes) {
        List<RoomTypeResponse> roomTypeList = new ArrayList<>();
        for (RoomType lesson : roomTypes.getContent()) {
            RoomTypeResponse lessonDTO = modelMapper.map(lesson, RoomTypeResponse.class);
            roomTypeList.add(lessonDTO);
        }
        return PaginateResponse.builder()
                .items(Collections.singletonList(roomTypeList))
                .totalItems(roomTypes.getTotalElements())
                .totalPages(roomTypes.getTotalPages())
                .currentPage(roomTypes.getNumber()).build();
    }
    @Override
    public PaginateResponse getAllRoomTypes(Pageable pageable) {
        Page<RoomType> roomTypes = roomTypeRepository.findRoomTypesByDeletedAtIsNull(pageable);
        return getPaginateResponse(roomTypes);
    }

    @Override
    public PaginateResponse getRoomTypeByHotelId(Pageable pageable, Long id) {
        Page<RoomType> roomTypes = roomTypeRepository.findRoomTypesByHotelIdEqualsAndDeletedAtIsNull(pageable, id);
        List<RoomTypeShortResponse> roomTypeList = new ArrayList<>();
        for (RoomType lesson : roomTypes.getContent()) {
            RoomTypeShortResponse lessonDTO = modelMapper.map(lesson, RoomTypeShortResponse.class);
            roomTypeList.add(lessonDTO);
        }
        return PaginateResponse.builder()
                .items(Collections.singletonList(roomTypeList))
                .totalItems(roomTypes.getTotalElements())
                .totalPages(roomTypes.getTotalPages())
                .currentPage(roomTypes.getNumber()).build();
    }

//    @Override
//    public PaginateResponse getRoomTypeByHotelId(Pageable pageable, Long id) {
//        Page<RoomType> hotels = roomTypeRepository.findRoomTypesByHotelIdEquals(pageable, id);
//        return getPaginateResponse(hotels);
//    }

    @Override
    public PaginateResponse getRoomTypeChargeLessThan(Pageable pageable, Integer charge) {
        Page<RoomType> hotels = roomTypeRepository.findRoomTypesByChargeLessThanAndDeletedAtIsNull(pageable, charge);
        return getPaginateResponse(hotels);
    }

    @Override
    public RoomTypeResponse getRoomTypeById(Long id) {
        Optional<RoomType> roomTypeOptional = roomTypeRepository.findById(id);
        if (roomTypeOptional.isEmpty()) {
            throw new RoomTypeNotExists();
        }
        return convertToResponse(roomTypeOptional.get());
    }

    @Override
    public RoomTypeShortResponse addRoomType(RoomTypeRequest roomTypeRequest) {
        RoomType roomType = modelMapper.map(roomTypeRequest, RoomType.class);

        Optional<Hotel> hotelOptional = hotelRepository.findById(roomTypeRequest.getHotelId());
        if (hotelOptional.isEmpty()) {
            throw new RoomTypeHotelNotExist();
        }
        if (AppHelper.hasSymbols(roomType.getName())) {
            throw new RoomTypeNameInvalid();
        }
        roomType.setId(null);
        roomTypeRepository.save(roomType);
        return convertToShortResponse(roomType);
    }

    @Override
    public RoomTypeResponse updateRoomType(RoomTypeRequest roomTypeRequest, Long id) {
        Optional<RoomType> optionalRoomType = roomTypeRepository.findById(id);

        if (optionalRoomType.isEmpty()) {
            throw new RoomTypeNotExists();
        }

        RoomType roomType = optionalRoomType.get();
        modelMapper.map(roomTypeRequest, roomType);
        RoomType updatedRoomType = roomTypeRepository.save(roomType);
        return convertToResponse(updatedRoomType);
    }

    @Override
    public void deleteRoomType(Long id) {
        Optional<RoomType> optionalRoomType = roomTypeRepository.findById(id);

        if (optionalRoomType.isEmpty()) {
            throw new RoomTypeNotExists();
        }

        RoomType roomType = optionalRoomType.get();
        roomType.setDeletedAt(LocalDateTime.now());
        roomType.setStatus(false);
        roomTypeRepository.save(roomType);
    }
}
