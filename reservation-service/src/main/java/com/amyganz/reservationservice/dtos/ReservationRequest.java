package com.amyganz.reservationservice.dtos;

import com.amyganz.reservationservice.entities.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class ReservationRequest implements Serializable {
    @JsonProperty(value = "user_id", required = true)
    private Integer user;
    @JsonAlias("hotel_id")
    @JsonProperty(value = "hotel_id", required = true)
    private Integer hotel;
    @JsonAlias("room_id")
    @JsonProperty(value = "room_id", required = true)
    private Integer room;
//    @JsonAlias("payment_id")
//    @JsonAlias("payment_id")
//    @JsonProperty(value = "paymentMethodId", required = true)
//    private Long paymentMethodId;
//    @JsonAlias("status_id")
    @JsonProperty(value = "total_payment", required = true)
    private Integer totalPayment;
    @JsonAlias("status_id")
    @JsonProperty(value = "reservation_status_id")
    private Long reservationStatusId = 1L;
//    @JsonAlias("night_spent")
    @JsonProperty("night_spent")
    private Long nightSpent;
    @JsonAlias("check_in_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty(value = "check_in_date", required = true)
    private Date checkInDate;
    @JsonAlias("check_out_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty(value = "check_out_date", required = true)
    private Date checkOutDate;
}
