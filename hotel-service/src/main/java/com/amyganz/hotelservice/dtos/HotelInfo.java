package com.amyganz.hotelservice.dtos;

import com.amyganz.hotelservice.entities.Room;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@JsonPropertyOrder({"id", "name", "class", "location", "ac_availability", "pool_availability", "restaurant_availability", "parking_availability", "status", "total_rooms", "room", "createdAt", "updatedAt", "deletedAt"})
public class HotelInfo {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String name;
    @JsonProperty(namespace = "class")
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
    @JsonProperty
    private List<Room> room;
    @JsonProperty
    private LocalDateTime createdAt;
    @JsonProperty
    private LocalDateTime updatedAt;
    @JsonProperty
    private LocalDateTime deletedAt;
}

