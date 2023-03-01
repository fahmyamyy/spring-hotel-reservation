package com.amyganz.reservationservice.dtos;

import com.amyganz.reservationservice.entities.PaymentMethod;
import com.amyganz.reservationservice.entities.Reservation;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class InvoiceRequest implements Serializable {
//    @JsonProperty(value = "payment_method", required = true)
    private PaymentMethod paymentMethod;

//    @JsonProperty(value = "payment_method", required = true)
    private Reservation reservation;
}

