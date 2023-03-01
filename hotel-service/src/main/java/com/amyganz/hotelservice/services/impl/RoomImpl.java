package com.amyganz.hotelservice.services.impl;

import com.amyganz.hotelservice.dtos.*;
import com.amyganz.hotelservice.dtos.RoomResponse;
import com.amyganz.hotelservice.entities.Hotel;
import com.amyganz.hotelservice.entities.Room;
import com.amyganz.hotelservice.entities.RoomType;
import com.amyganz.hotelservice.exceptions.ServerException;
import com.amyganz.hotelservice.exceptions.room.RoomAlreadyExists;
import com.amyganz.hotelservice.exceptions.room.RoomHotelNotExists;
import com.amyganz.hotelservice.exceptions.room.RoomNotExists;
import com.amyganz.hotelservice.exceptions.room.RoomRoomTypeNotExists;
import com.amyganz.hotelservice.helpers.AppHelper;
import com.amyganz.hotelservice.repositories.HotelRepository;
import com.amyganz.hotelservice.repositories.RoomRepository;
import com.amyganz.hotelservice.repositories.RoomTypeRepository;
import com.amyganz.hotelservice.services.RoomService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
public class RoomImpl implements RoomService {
    private RoomRepository roomRepository;
    private HotelRepository hotelRepository;
    private RoomTypeRepository roomTypeRepository;
    private ModelMapper modelMapper;
    private RoomResponse convertToResponse(Room room) {return modelMapper.map(room, RoomResponse.class);}
    private RoomShortResponse convertToShortResponse(Room room) {return modelMapper.map(room, RoomShortResponse.class);}

    private PaginateResponse getPaginateResponse(Page<Room> rooms) {
        List<RoomResponse> roomList = new ArrayList<>();
        for (Room room : rooms.getContent()) {
            RoomResponse roomDTO = modelMapper.map(room, RoomResponse.class);
            roomList.add(roomDTO);
        }
        return PaginateResponse.builder()
                .items(Collections.singletonList(roomList))
                .totalItems(rooms.getTotalElements())
                .totalPages(rooms.getTotalPages())
                .currentPage(rooms.getNumber()).build();
    }

    @Override
    @Cacheable(value = "room")
    public PaginateResponse getAllRoom(Pageable pageable) {
        Page<Room> room = roomRepository.findAll(pageable);
        return getPaginateResponse(room);
    }

    @Override
    @Cacheable(value = "room")
    public PaginateResponse getRoomByHotelId(Pageable pageable, Long id) {
        Page<Room> room = roomRepository.findRoomsByHotelIdEqualsAndDeletedAtIsNull(pageable, id);
        return getPaginateResponse(room);
    }

    @Override
    @Cacheable(value = "room")
    public PaginateResponse getRoomByRoomType(Pageable pageable, Long typeId) {
        Page<Room> room = roomRepository.findRoomsByRoomTypeIdEqualsAndDeletedAtIsNull(pageable, typeId);
        return getPaginateResponse(room);
    }

    @Override
    @Cacheable(value = "room")
    public PaginateResponse getRoomByAvailability(Pageable pageable) {
        Page<Room> room = roomRepository.findRoomsByAvailableIsTrueAndDeletedAtIsNull(pageable);
        return getPaginateResponse(room);
    }

    @Override
    @Cacheable(value = "room")
    public PaginateResponse getRoomByHotelAndRoomType(Pageable pageable, Long hotelId, Long typeId) {
        Page<Room> room = roomRepository.findRoomsByHotelIdEqualsAndRoomTypeIdEquals(pageable, hotelId, typeId);
        return getPaginateResponse(room);
    }

    @Override
    @Cacheable(value = "room")
    public RoomResponse getRoomById(Long id) {
        Optional<Room> roomOptional = roomRepository.findById(id);
        if (!roomOptional.isPresent()) {
            throw new RoomNotExists();
        }
        return convertToResponse(roomOptional.get());
    }

    @Override
    @CacheEvict(value = {"room", "hotel"}, allEntries = true)
    public RoomShortResponse addRoom(RoomRequest roomRequest) {
        Room room = modelMapper.map(roomRequest, Room.class);
        Optional<Hotel> hotelOptional = hotelRepository.findById(roomRequest.getHotelId());

        if (hotelOptional.isEmpty()) {
            throw new RoomHotelNotExists();
        }

        // PREVENT FOR USER ADDING ROOM WITH RANDOM ROOMTYPE ID
        Optional<RoomType> roomTypeOptional = roomTypeRepository.findById(roomRequest.getRoomTypeId());
        if (roomTypeOptional.isEmpty()) {
            throw new RoomRoomTypeNotExists();
        }
        if (roomTypeOptional.get().getHotel().getId() != roomRequest.getHotelId()) {
            throw new RoomRoomTypeNotExists();
        }
        // UDAH BENER
        Optional<Room> roomOptional = roomRepository.findByRoomNumberAndHotel(roomRequest.getRoomNumber(), hotelOptional.get());

        if (roomOptional.isPresent()) {
            throw new RoomAlreadyExists();
        }

        try {
            room.setId(null);
            roomRepository.save(room);

            Hotel hotel = hotelOptional.get();
            hotel.setTotal_rooms(hotel.getTotal_rooms() + 1);
            hotelRepository.save(hotel);
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
        return convertToShortResponse(room);
    }

    @Override
    @CacheEvict(value = "room", allEntries = true)
    public RoomResponse updateRoom(RoomRequest roomRequest, Long id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);

        if (optionalRoom.isEmpty()) {
            throw new RoomNotExists();
        }

        Room room = optionalRoom.get();
        modelMapper.map(roomRequest, room);
        Room updatedRoom = roomRepository.save(room);
        return convertToResponse(updatedRoom);
    }

    @Override
    @CacheEvict(value = "room", allEntries = true)
    public void unavailablingRoom(Long id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);

        if (optionalRoom.isEmpty()) {
            throw new RoomNotExists();
        }

        Room room = optionalRoom.get();
        room.setAvailable(!room.getAvailable());
        roomRepository.save(room);
    }

    @Override
    @CacheEvict(value = "room", allEntries = true)
    public void deleteRoom(Long id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);

        if (optionalRoom.isEmpty()) {
            throw new RoomNotExists();
        }

        Room room = optionalRoom.get();
        room.setDeletedAt(LocalDateTime.now());
        room.setStatus(false);
        roomRepository.save(room);
    }
}
