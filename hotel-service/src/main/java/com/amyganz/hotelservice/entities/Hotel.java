package com.amyganz.hotelservice.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "hotel")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Hotel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotelId", unique = true)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false, name = "class")
    private Integer cls;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private Boolean ac_availability = false;
    @Column(nullable = false)
    private Boolean wifi_availability = false;
    @Column(nullable = false)
    private Boolean pool_availability = false;
    @Column(nullable = false)
    private Boolean restaurant_availability = false;
    @Column(nullable = false)
    private Boolean parking_availability = false;
    @Column(nullable = false)
    private Boolean status = true;
    @Column(nullable = false)
    private Integer total_rooms = 0;
    @JsonManagedReference
    @OneToMany(mappedBy="hotel")
    private List<Room> room;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
