package com.amyganz.hotelservice.dtos;

import com.amyganz.hotelservice.entities.Hotel;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@JsonPropertyOrder({"id", "code", "name", "description", "charge", "status", "hotel"})
public class RoomTypeResponse implements Serializable {
    private Long id;
//    @JsonProperty
//    private String code;
    @JsonProperty
    private String name;
    @JsonProperty
    private String description;
    @JsonProperty
    private Integer charge;
    @JsonProperty
    private Boolean status;
    @JsonProperty("hotel")
    private HotelResponse hotel;
}
