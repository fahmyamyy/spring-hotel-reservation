package com.amyganz.reservationservice.dtos;

import com.amyganz.reservationservice.entities.PaymentMethod;
import com.amyganz.reservationservice.entities.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@JsonPropertyOrder({"id", "user", "hotel", "room", "payment", "status", "night_spent", "check_in_date", "check_out_date"})
public class ReservationResponse<T> implements Serializable {

    @JsonProperty
    private Long id;
    @JsonProperty("user")
    private T user;
    @JsonProperty("hotel")
    private T hotel;
    @JsonProperty("room")
    private T room;
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
