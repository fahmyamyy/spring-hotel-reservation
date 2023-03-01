package com.amyganz.hotelservice.controllers;

import com.amyganz.hotelservice.dtos.*;
import com.amyganz.hotelservice.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Collections;

@RestController
@RequestMapping("api/v1/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping
//    @Cacheable("rooms")
    public ResponseDTO<PaginateResponse> getAllRoom(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "5") int limit
    ) {
        int offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit);

        PaginateResponse paginateResponse = roomService.getAllRoom(pageable);
        return ResponseDTO.<PaginateResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully fetch all Room datas")
                .data(paginateResponse).build();
    }

    @GetMapping("available")
//    @Cacheable("rooms")
    public ResponseDTO<PaginateResponse> getAllAvailableRoom(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "5") int limit
    ) {
        int offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit);

        PaginateResponse paginateResponse = roomService.getRoomByAvailability(pageable);
        return ResponseDTO.<PaginateResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully fetch all available Room datas")
                .data(paginateResponse).build();
    }

    @GetMapping("hotel")
//    @Cacheable("rooms")
    public ResponseDTO<PaginateResponse> getRoomByHotelId(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            @RequestParam(name = "id") Long id
    ) {
        int offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit);

        PaginateResponse paginateResponse = roomService.getRoomByHotelId(pageable, id);
        return ResponseDTO.<PaginateResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully fetch Room datas with Hotel id: " + id)
                .data(paginateResponse).build();
    }

    @GetMapping("roomtype")
//    @Cacheable("rooms")
    public ResponseDTO<PaginateResponse> getRoomByRoomType(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            @RequestParam(name = "roomtype", defaultValue = "1") Long roomType
    ) {
        int offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit);

        PaginateResponse paginateResponse = roomService.getRoomByRoomType(pageable, roomType);

        return ResponseDTO.<PaginateResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully fetch Room datas with room type: " + roomType)
                .data(paginateResponse).build();
    }

    @GetMapping("class")
//    @Cacheable("rooms")
    public ResponseDTO<PaginateResponse> getRoomByHotelAndRoomType(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            @RequestParam(name = "hotel") Long hotelId,
            @RequestParam(name = "type") Long typeId
    ) {
        int offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit);

        PaginateResponse paginateResponse = roomService.getRoomByHotelAndRoomType(pageable, hotelId, typeId);
        return ResponseDTO.<PaginateResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully fetch Room datas with HotelId: " + hotelId + " And TypeId: " + typeId)
                .data(paginateResponse).build();
    }

    @GetMapping("id/{id}")
//    @Cacheable("rooms")
    public ResponseDTO<RoomResponse> getRoomById(@PathVariable Long id) {
        RoomResponse roomResponse = roomService.getRoomById(id);
        return ResponseDTO.<RoomResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully fetch Room data with id: " + id)
                .data(roomResponse).build();
    }


    @Transactional
    @PostMapping
    @CacheEvict(value = "rooms" ,allEntries = true)
    public ResponseEntity<ResponseDTO> addRoom(@RequestBody RoomRequest roomRequest) {
        RoomShortResponse roomResponse = roomService.addRoom(roomRequest);
        return new ResponseEntity<ResponseDTO>(ResponseDTO.builder()
                .httpStatus(HttpStatus.CREATED)
                .status(true)
                .message("Successfully creating new Room")
                .data(roomResponse).build(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "rooms" ,allEntries = true)
    public ResponseEntity<ResponseDTO<RoomResponse>> updateRoom(@RequestBody RoomRequest roomRequest, @PathVariable Long id) {
        RoomResponse roomResponse = roomService.updateRoom(roomRequest, id);
        return new ResponseEntity<ResponseDTO<RoomResponse>>(ResponseDTO.<RoomResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully updating Room with id: " + id)
                .data(roomResponse)
                .build(), HttpStatus.OK);
    }

    @PutMapping("unavailabling/{id}")
    @CacheEvict(value = "rooms" ,allEntries = true)
    public ResponseEntity<ResponseDTO> updateRoom(@PathVariable Long id) {
        roomService.unavailablingRoom(id);
        return new ResponseEntity<ResponseDTO>(ResponseDTO.builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully updating Room with id: " + id)
                .data(Collections.emptyList())
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "rooms" ,allEntries = true)
    public ResponseEntity<ResponseDTO> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return new ResponseEntity<ResponseDTO>(ResponseDTO.builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully deleting Room with id: " + id)
                .data(Collections.emptyList())
                .build(), HttpStatus.OK);
    }
}
