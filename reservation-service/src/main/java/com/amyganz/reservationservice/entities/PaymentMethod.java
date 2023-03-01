package com.amyganz.reservationservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="payment_method")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PaymentMethod implements Serializable {
    @Id
    @Column(name = "paymentMethodId", unique = true)
    private Long id;

    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @JsonIgnoreProperties
    @OneToMany(mappedBy="paymentMethod")
    @Column()
    private List<Invoice> invoices = Collections.EMPTY_LIST;

//    @OneToMany(mappedBy="payment")
//    private List<Reservation> reservations;
}
