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
@JsonPropertyOrder({"id", "name", "class", "location", "ac_availability", "wifi_availability", "pool_availability", "restaurant_availability", "parking_availability", "status", "total_rooms", "room"})
public class HotelResponse implements Serializable {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String name;
    @JsonProperty("class")
    private Integer cls;
    @JsonProperty
    private String location;
    @JsonProperty
    private Boolean ac_availability;
    @JsonProperty
    private Boolean wifi_availability;
    @JsonProperty
    private Boolean pool_availability;
    @JsonProperty
    private Boolean restaurant_availability;
    @JsonProperty
    private Boolean parking_availability;
    @JsonProperty
    private Boolean status;
    @JsonProperty
    private Integer total_rooms;
//    @JsonProperty
//    private List<Room> room;
}
