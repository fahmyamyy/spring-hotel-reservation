package com.amyganz.hotelservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class RoomRequest {
    @JsonProperty(required = true)
    private Integer roomNumber;
    @JsonProperty(required = true)
    private Long hotelId;
    @JsonProperty(required = true)
    private Long roomTypeId;
}
