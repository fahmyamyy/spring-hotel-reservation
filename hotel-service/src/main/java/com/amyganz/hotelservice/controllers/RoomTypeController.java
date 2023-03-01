package com.amyganz.hotelservice.controllers;

import com.amyganz.hotelservice.dtos.*;
import com.amyganz.hotelservice.services.RoomTypeService;
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
@RequestMapping("api/v1/roomtype")
public class RoomTypeController {
    @Autowired
    private RoomTypeService roomTypeService;

    @GetMapping
    @Cacheable("roomTypes")
    public ResponseDTO<PaginateResponse> getAllRoomType(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "5") int limit
    ) {
        int offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit);

        PaginateResponse paginateResponse = roomTypeService.getAllRoomTypes(pageable);
        return ResponseDTO.<PaginateResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully fetch RoomType datas")
                .data(paginateResponse).build();
    }

//    @GetMapping("name")
//    @Cacheable("roomTypes")
//    public ResponseDTO<PaginateResponse> getRoomTypeByHotelId(
//            @RequestParam(name = "page", defaultValue = "1") int page,
//            @RequestParam(name = "limit", defaultValue = "5") int limit,
//            @RequestParam(name = "id") Long id
//    ) {
//        int offset = page - 1;
//        Pageable pageable = PageRequest.of(offset, limit);
//
//        PaginateResponse paginateResponse = roomTypeService.getRoomTypeByHotelId(pageable, id);
//        return ResponseDTO.<PaginateResponse>builder()
//                .httpStatus(HttpStatus.OK)
//                .status(true)
//                .message("Successfully fetch RoomType datas with id contains: " + id)
//                .data(paginateResponse).build();
//    }

    @GetMapping("charge")
    @Cacheable("roomTypes")
    public ResponseDTO<PaginateResponse> getRoomTypeChargeLessThan(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            @RequestParam(name = "charge") Integer charge
    ) {
        int offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit);

        PaginateResponse paginateResponse = roomTypeService.getRoomTypeChargeLessThan(pageable, charge);
        return ResponseDTO.<PaginateResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully fetch RoomType datas with charge less than: Rp. " + charge)
                .data(paginateResponse).build();
    }

    @GetMapping("hotel")
    @Cacheable("roomTypes")
    public ResponseDTO<PaginateResponse> getRoomTypeByHotelId(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            @RequestParam(name = "id") Long id
    ) {
        int offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit);

        PaginateResponse paginateResponse = roomTypeService.getRoomTypeByHotelId(pageable, id);
        return ResponseDTO.<PaginateResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully fetch RoomType datas with Hotel id: " + id)
                .data(paginateResponse).build();
    }

    @GetMapping("id/{id}")
    @Cacheable("roomTypes")
    public ResponseDTO<RoomTypeResponse> getRoomTypeById(@PathVariable Long id) {
        RoomTypeResponse roomTypeResponse = roomTypeService.getRoomTypeById(id);
        return ResponseDTO.<RoomTypeResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully fetch RoomType data with id: " + id)
                .data(roomTypeResponse).build();
    }


    @Transactional
    @PostMapping
    @CacheEvict(value = "roomTypes" ,allEntries = true)
    public ResponseEntity<ResponseDTO> addRoomType(@RequestBody RoomTypeRequest roomTypeRequest) {
        RoomTypeShortResponse roomTypeResponse = roomTypeService.addRoomType(roomTypeRequest);
        return new ResponseEntity<ResponseDTO>(ResponseDTO.builder()
                .httpStatus(HttpStatus.CREATED)
                .status(true)
                .message("Successfully creating new RoomType")
                .data(roomTypeResponse).build(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "roomTypes" ,allEntries = true)
    public ResponseEntity<ResponseDTO<RoomTypeResponse>> updateRoomType(@RequestBody RoomTypeRequest roomTypeRequest, @PathVariable Long id) {
        RoomTypeResponse roomTypeResponse = roomTypeService.updateRoomType(roomTypeRequest, id);
        return new ResponseEntity<ResponseDTO<RoomTypeResponse>>(ResponseDTO.<RoomTypeResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully updating RoomType with id: " + id)
                .data(roomTypeResponse)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "roomTypes" ,allEntries = true)
    public ResponseEntity<ResponseDTO> deleteRoomType(@PathVariable Long id) {
        roomTypeService.deleteRoomType(id);
        return new ResponseEntity<ResponseDTO>(ResponseDTO.builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully deleting RoomType with id: " + id)
                .data(Collections.emptyList())
                .build(), HttpStatus.OK);
    }
}
