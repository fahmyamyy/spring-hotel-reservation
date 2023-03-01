package com.amyganz.hotelservice.controllers;

import com.amyganz.hotelservice.dtos.HotelRequest;
import com.amyganz.hotelservice.dtos.HotelResponse;
import com.amyganz.hotelservice.dtos.PaginateResponse;
import com.amyganz.hotelservice.dtos.ResponseDTO;
import com.amyganz.hotelservice.services.HotelService;
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
@RequestMapping("api/v1/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @GetMapping
//    @Cacheable("hotels")
    public ResponseDTO<PaginateResponse> getAllHotel(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "5") int limit
    ) {
        int offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit);

        PaginateResponse paginateResponse = hotelService.getAllHotel(pageable);
        return ResponseDTO.<PaginateResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully fetch Hotel datas")
                .data(paginateResponse).build();
    }

    @GetMapping("name")
//    @Cacheable("hotels")
    public ResponseDTO<PaginateResponse> getHotelByName(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            @RequestParam(name = "name") String name
    ) {
        int offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit);

        PaginateResponse paginateResponse = hotelService.getHotelByName(pageable, name);
//        return new ResponseEntity<ResponseDTO<PaginateResponse>>(ResponseDTO
//                .<PaginateResponse>builder()
//                .httpStatus(HttpStatus.OK)
//                .status(true)
//                .message("Successfully fetch Hotel datas with name contains: " + name)
//                .data(paginateResponse).build(), HttpStatus.OK);
        return ResponseDTO.<PaginateResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully fetch Hotel datas with name contains: " + name)
                .data(paginateResponse).build();
    }

    @GetMapping("location")
//    @Cacheable("hotels")
    public ResponseDTO<PaginateResponse> getHotelByLocation(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            @RequestParam(name = "location") String location
    ) {
        int offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit);

        PaginateResponse paginateResponse = hotelService.getHotelByLocation(pageable, location);
//        return new ResponseEntity<ResponseDTO<PaginateResponse>>(ResponseDTO
//                .<PaginateResponse>builder()
//                .httpStatus(HttpStatus.OK)
//                .status(true)
//                .message("Successfully fetch Hotel datas with location contains: " + location)
//                .data(paginateResponse).build(), HttpStatus.OK);
        return ResponseDTO.<PaginateResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully fetch Hotel datas with location contains: " + location)
                .data(paginateResponse).build();
    }

    @GetMapping("class")
//    @Cacheable("hotels")
    public ResponseDTO<PaginateResponse> getHotelByClass(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            @RequestParam(name = "class") Integer cls
    ) {
        int offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit);

        PaginateResponse paginateResponse = hotelService.getHotelByClass(pageable, cls);
        return ResponseDTO.<com.amyganz.hotelservice.dtos.PaginateResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully fetch Hotel datas with class: " + cls)
                .data(paginateResponse).build();
    }

    @GetMapping("id/{id}")
//    @Cacheable("hotels")
    public ResponseDTO<HotelResponse> getHotelById(@PathVariable Long id) {
        HotelResponse hotelResponse = hotelService.getHotelById(id);
        return ResponseDTO.<HotelResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully fetch Hotel data with id: " + id)
                .data(hotelResponse).build();
    }


    @Transactional
    @PostMapping
    @CacheEvict(value = "hotels" ,allEntries = true)
    public ResponseEntity<ResponseDTO> addHotel(@RequestBody HotelRequest hotelRequest) {
        HotelResponse hotelResponse = hotelService.addHotel(hotelRequest);
        return new ResponseEntity<ResponseDTO>(ResponseDTO.builder()
                .httpStatus(HttpStatus.CREATED)
                .status(true)
                .message("Successfully creating new Hotel")
                .data(hotelResponse).build(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "hotels" ,allEntries = true)
    public ResponseEntity<ResponseDTO<HotelResponse>> updateHotel(@RequestBody HotelRequest hotelRequest, @PathVariable Long id) {
        HotelResponse hotelResponse = hotelService.updateHotel(hotelRequest, id);
        return new ResponseEntity<ResponseDTO<HotelResponse>>(ResponseDTO.<HotelResponse>builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully updating Hotel with id: " + id)
                .data(hotelResponse)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "hotels" ,allEntries = true)
    public ResponseEntity<ResponseDTO> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return new ResponseEntity<ResponseDTO>(ResponseDTO.builder()
                .httpStatus(HttpStatus.OK)
                .status(true)
                .message("Successfully deleting Hotel with id: " + id)
                .data(Collections.emptyList())
                .build(), HttpStatus.OK);
    }

}
