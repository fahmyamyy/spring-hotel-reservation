package com.amyganz.hotelservice.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "room_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RoomType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomTypeId", unique = true)
    private Long id;

//    @Column(unique = true, nullable = false)
//    private String code;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Integer charge;
    @Column(nullable = false)
    private Boolean status = true;
    @ManyToOne
    @JoinColumn(name="hotelId", nullable=false)
    @JsonBackReference
    private Hotel hotel;
    @JsonManagedReference
    @OneToMany(mappedBy="roomType")
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
