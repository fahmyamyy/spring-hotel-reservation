package com.amyganz.reservationservice.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="reservation")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservationId", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Integer user;
    @Column(name = "hotel_id", nullable = false)
    private Integer hotel;
    @Column(name = "room_id", nullable = false)
    private Integer room;

    @Column(nullable = false)
    private Integer totalPayment;
//    @Column(name = "payment_id", nullable = false)
//    private Integer payment;
//    @OneToMany(mappedBy="hotel")
//    @Column(name = "payment_id", nullable = false)
//    private Set<PaymentMethod> payment;
//    @ManyToOne
//    @JoinColumn(name="paymentMethodId", nullable=false)
////    @OnDelete(action = OnDeleteAction.CASCADE)
//    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name="reservationStatusId", nullable=false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private ReservationStatus reservationStatus;
//    @Column(name = "status_id", nullable = false)
//    private Integer status;
    @Column(name = "night_spent", nullable = false)
    private Long nightSpent;
    @Column(name = "check_in_date", nullable = false, updatable = false)
    private Date checkInDate;
    @Column(name = "check_out_date", nullable = false)
    private Date checkOutDate;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at", updatable = false)
    private LocalDateTime deletedAt;
}
