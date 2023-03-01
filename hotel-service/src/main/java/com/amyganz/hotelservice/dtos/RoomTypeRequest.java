package com.amyganz.hotelservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class RoomTypeRequest {
    @JsonProperty(required = true)
    private Long hotelId;
//    @JsonProperty(required = true)
//    private String code;
    @JsonProperty(required = true)
    private String name;
    @JsonProperty(required = true)
    private String description;
    @JsonProperty(required = true)
    private Integer charge;
}
