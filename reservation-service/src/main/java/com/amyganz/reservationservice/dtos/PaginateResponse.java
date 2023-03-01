package com.amyganz.reservationservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonPropertyOrder({"currentPage", "totalPages", "totalItems", "items"})
public class PaginateResponse<T> implements Serializable {
    @JsonProperty
    private List<T> items;
    @JsonProperty
    private Long totalItems;
    @JsonProperty
    private Integer currentPage;
    @JsonProperty
    private Integer totalPages;
}

