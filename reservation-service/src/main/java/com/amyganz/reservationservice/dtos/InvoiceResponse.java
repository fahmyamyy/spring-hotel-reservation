package com.amyganz.reservationservice.dtos;

import com.amyganz.reservationservice.entities.PaymentMethod;
import com.amyganz.reservationservice.entities.Reservation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class InvoiceResponse {
    @JoinColumn(name = "reservationId")
    private Reservation reservation;
    @JoinColumn(name="paymentMethodId", nullable=false)
    private PaymentMethod paymentMethod;
}

