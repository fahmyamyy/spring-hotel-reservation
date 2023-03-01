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
@JsonPropertyOrder({"id", "hotel_id", "code", "name", "description", "charge", "status"})
public class RoomTypeShortResponse implements Serializable {
    private Long id;
    @JsonProperty("hotel_id")
    private Long hotelId;
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
}
