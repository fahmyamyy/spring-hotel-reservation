package com.amyganz.hotelservice.dtos;

import com.amyganz.hotelservice.entities.Hotel;
import com.amyganz.hotelservice.entities.RoomType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@JsonPropertyOrder({"id", "room_number", "hotel", "room_type", "available", "status"})
public class RoomResponse implements Serializable {
    @JsonProperty
    private Long id;
    @JsonProperty("room_number")
    private Integer roomNumber;
    @JsonProperty
    private Boolean available;
    @JsonProperty
    private Boolean status;
    @JsonProperty
    private HotelResponse hotel;
    @JsonProperty("room_type")
    private RoomTypeShortResponse roomType;
}
