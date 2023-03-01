package com.amyganz.hotelservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@JsonPropertyOrder({"id", "hotel_id", "room_type_id", "room_number", "available", "status"})
public class RoomShortResponse implements Serializable {
    @JsonProperty
    private Long id;
    @JsonProperty("room_number")
    private Integer roomNumber;
    @JsonProperty
    private Boolean available;
    @JsonProperty
    private Boolean status;
    @JsonProperty("hotel_id")
    private Long hotelId;
    @JsonProperty("room_type_id")
    private Long roomTypeId;
}
