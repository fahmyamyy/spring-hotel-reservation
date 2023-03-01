package com.amyganz.reservationservice.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@JsonPropertyOrder({"id", "room_number", "available", "status", "room_type"})
public class RoomResponse implements Serializable {
    @JsonProperty
    private Long id;
    @JsonProperty("room_number")
    private Integer roomNumber;
//    @JsonIgnore
    private Boolean available;
    @JsonIgnore
    private Boolean status;
    @JsonIgnore
    @JsonProperty("hotel")
    private HotelResponse hotel;
    @JsonProperty("room_type")
    private RoomTypeShortResponse roomType;
}
