package com.amyganz.reservationservice.dtos;

import com.amyganz.reservationservice.entities.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@JsonPropertyOrder({"id", "user_id", "hotel_id", "room_id", "payment", "status", "night_spent", "check_in_date", "check_out_date"})
public class ReservationShortResponse implements Serializable {

    @JsonProperty
    private Long id;
    @JsonProperty("user_id")
    private Integer user;
    @JsonProperty("hotel_id")
    private Integer hotel;
    @JsonProperty("room_id")
    private Integer room;
    @JsonProperty("total_payment")
    private Integer totalPayment;
    @JsonProperty("status")
    private ReservationStatus status;
    @JsonProperty("night_spent")
    private Long nightSpent;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
    @JsonProperty("check_in_date")
    private Date checkInDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
    @JsonProperty("check_out_date")
    private Date checkOutDate;
}
