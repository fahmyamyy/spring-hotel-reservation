package com.amyganz.reservationservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="reservation_status")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ReservationStatus implements Serializable {
    @Id
    @Column(name = "reservationStatusId", unique = true)
    private Long id;

    @Column(nullable = false)
    private String name;
    @JsonIgnore
    @JsonIgnoreProperties
    @OneToMany(mappedBy="reservationStatus")
    private List<Reservation> reservations = Collections.EMPTY_LIST;
}
