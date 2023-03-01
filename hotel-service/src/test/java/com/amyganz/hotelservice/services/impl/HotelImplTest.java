package com.amyganz.hotelservice.services.impl;

import com.amyganz.hotelservice.dtos.HotelRequest;
import com.amyganz.hotelservice.dtos.HotelResponse;
import com.amyganz.hotelservice.dtos.PaginateResponse;
import com.amyganz.hotelservice.entities.Hotel;
import com.amyganz.hotelservice.repositories.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
class HotelImplTest {
    @Mock
    HotelRepository hotelRepository;

    @InjectMocks
    HotelImpl hotelService = Mockito.spy(new HotelImpl());
    ModelMapper modelMapper = Mockito.spy(new ModelMapper());

    private PaginateResponse getPaginateResponse(Page<Hotel> hotels) {
        List<HotelResponse> hotelList = new ArrayList<>();
        for (Hotel lesson : hotels.getContent()) {
            HotelResponse lessonDTO = modelMapper.map(lesson, HotelResponse.class);
            hotelList.add(lessonDTO);
        }
        return PaginateResponse.builder()
                .items(Collections.singletonList(hotelList))
                .totalItems(hotels.getTotalElements())
                .totalPages(hotels.getTotalPages())
                .currentPage(hotels.getNumber()).build();
    }
    List<HotelRequest> hotelRequestsDatas = new ArrayList<>(List.of(
            new HotelRequest("Hotel Kaisar", 4, "Duren Tiga, Jakarta Selatan", true, true, true, true,true),
            new HotelRequest("Mandarin Oriental", 5, "Thamrin, Jakarta Pusat", true, true, true, true,true),
            new HotelRequest("Pullman Jakarta", 5, "Thamrin, Jakarta Pusat", true, true, true, true,true)
    ));

    List<Hotel> hotelDatas = hotelRequestsDatas.stream()
            .map(hotel -> modelMapper.map(hotel, Hotel.class))
            .collect(Collectors.toList());

    @BeforeEach
    void setUp() {


        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllHotel() {
        // Arrange
        Page mockPage = Mockito.mock(Page.class);

        Mockito.when(mockPage.getTotalElements()).thenReturn((long) hotelDatas.size());
        Mockito.when(mockPage.getContent()).thenReturn(hotelDatas);
        Mockito.when(hotelRepository.findHotelsByDeletedAtIsNull(Mockito.any())).thenReturn(mockPage);

        PaginateResponse expectedResponse = getPaginateResponse(mockPage);

        // Act
        PaginateResponse actualResponse = hotelService.getAllHotel(PageRequest.of(0, 5));

        // Assert
        assertEquals(expectedResponse.getCurrentPage(), actualResponse.getCurrentPage());
        assertEquals(expectedResponse.getTotalPages(), actualResponse.getTotalPages());
        assertEquals(expectedResponse.getTotalItems(), actualResponse.getTotalItems());
        assertEquals(expectedResponse.getItems(), actualResponse.getItems());
    }

    @Test
    void getHotelByLocation() {
        // Arrange
        Page mockPage = Mockito.mock(Page.class);

        Mockito.when(mockPage.getTotalElements()).thenReturn((long) hotelDatas.size());
        Mockito.when(mockPage.getContent()).thenReturn(hotelDatas);
        Mockito.when(hotelRepository.findByLocationContainsAndDeletedAtIsNull(PageRequest.of(0, 5), "Jak")).thenReturn(mockPage);

        PaginateResponse expectedResponse = getPaginateResponse(mockPage);

        // Act
        PaginateResponse actualResponse = hotelService.getHotelByLocation(PageRequest.of(0, 5), "Jak");

        // Assert
        assertEquals(expectedResponse.getCurrentPage(), actualResponse.getCurrentPage());
        assertEquals(expectedResponse.getTotalPages(), actualResponse.getTotalPages());
        assertEquals(expectedResponse.getTotalItems(), actualResponse.getTotalItems());
        assertEquals(expectedResponse.getItems(), actualResponse.getItems());
    }

    @Test
    void getHotelByClass() {
        // Arrange
        Page mockPage = Mockito.mock(Page.class);
        hotelDatas.remove(0);

        Mockito.when(mockPage.getTotalElements()).thenReturn((long) hotelDatas.size());
        Mockito.when(mockPage.getContent()).thenReturn(hotelDatas);
        Mockito.when(hotelRepository.findByClsEqualsAndDeletedAtIsNull(PageRequest.of(0, 5), 5)).thenReturn(mockPage);

        PaginateResponse expectedResponse = getPaginateResponse(mockPage);

        // Act
        PaginateResponse actualResponse = hotelService.getHotelByClass(PageRequest.of(0, 5), 5);

        // Assert
        assertEquals(expectedResponse.getCurrentPage(), actualResponse.getCurrentPage());
        assertEquals(expectedResponse.getTotalPages(), actualResponse.getTotalPages());
        assertEquals(expectedResponse.getTotalItems(), actualResponse.getTotalItems());
        assertEquals(expectedResponse.getItems(), actualResponse.getItems());
    }

    @Test
    void getHotelByName() {
        // Arrange
        Page mockPage = Mockito.mock(Page.class);
        hotelDatas.remove(1);

        Mockito.when(mockPage.getTotalElements()).thenReturn((long) hotelDatas.size());
        Mockito.when(mockPage.getContent()).thenReturn(hotelDatas);
        Mockito.when(hotelRepository.findByNameContainsAndDeletedAtIsNull(PageRequest.of(0, 5), "ka")).thenReturn(mockPage);

        PaginateResponse expectedResponse = getPaginateResponse(mockPage);

        // Act
        PaginateResponse actualResponse = hotelService.getHotelByName(PageRequest.of(0, 5), "ka");

        // Assert
        assertEquals(expectedResponse.getCurrentPage(), actualResponse.getCurrentPage());
        assertEquals(expectedResponse.getTotalPages(), actualResponse.getTotalPages());
        assertEquals(expectedResponse.getTotalItems(), actualResponse.getTotalItems());
        assertEquals(expectedResponse.getItems(), actualResponse.getItems());
    }

    @Test
    void getHotelById() {
        // Arrange
        HotelResponse expectedResponse = modelMapper.map(hotelDatas.get(0), HotelResponse.class);
        Mockito.when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotelDatas.get(0)));

        // Act
        HotelResponse actualResponse = hotelService.getHotelById(1L);

        // Assert
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getName(), actualResponse.getName());
        assertEquals(expectedResponse.getName(), actualResponse.getName());
        assertEquals(expectedResponse.getCls(), actualResponse.getCls());
        assertEquals(expectedResponse.getLocation(), actualResponse.getLocation());
        assertEquals(expectedResponse.getAc_availability(), actualResponse.getAc_availability());
        assertEquals(expectedResponse.getWifi_availability(), actualResponse.getWifi_availability());
        assertEquals(expectedResponse.getPool_availability(), actualResponse.getPool_availability());
        assertEquals(expectedResponse.getRestaurant_availability(), actualResponse.getRestaurant_availability());
        assertEquals(expectedResponse.getParking_availability(), actualResponse.getParking_availability());
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getTotal_rooms(), actualResponse.getTotal_rooms());



    }

    @Test
    void addHotel() {
    }

    @Test
    void updateHotel() {
    }

    @Test
    void deleteHotel() {
        Hotel expectedResponse = hotelDatas.get(0);
        expectedResponse.setDeletedAt(LocalDateTime.now());
        expectedResponse.setStatus(false);

        Mockito.when(hotelRepository.save(Mockito.any())).thenReturn(expectedResponse);

        hotelRepository.deleteById(1L);

        Assert.hasText(expectedResponse.getDeletedAt().toString());
        assertFalse(expectedResponse.getStatus());
    }
}