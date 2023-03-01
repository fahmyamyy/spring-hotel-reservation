package com.amyganz.hotelservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class HotelRequest {
    @JsonProperty(required = true)
    private String name;
    @JsonProperty(required = true, namespace = "class")
    private Integer cls;
    @JsonProperty(required = true)
    private String location;
    private Boolean ac_availability;
    private Boolean wifi_availability;
    private Boolean pool_availability;
    private Boolean restaurant_availability;
    private Boolean parking_availability;
}
