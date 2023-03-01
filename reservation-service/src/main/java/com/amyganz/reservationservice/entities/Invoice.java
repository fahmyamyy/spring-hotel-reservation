package com.amyganz.reservationservice.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="invoice")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Invoice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservationId", unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name="paymentMethodId", nullable=false)
    private PaymentMethod paymentMethod;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "reservationId")
    private Reservation reservation;
}
