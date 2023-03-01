//package com.amyganz.authservice.entities;
//
//import lombok.*;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.Date;
//
//@Entity
//@Table(name = "receptionist")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@ToString
//public class Receptionist {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", unique = true)
//    private Long id;
//    @Column(unique = true, nullable = false)
//    private Long hotelId;
//    @Column(unique = true, nullable = false)
//    private String email;
//    @Column(unique = true, nullable = false)
//    private String username;
//    @Column(nullable = false)
//    private String password;
//    @CreationTimestamp
//    @Column(name = "created_at", nullable = false, updatable = false)
//    private LocalDateTime createdAt;
//    @UpdateTimestamp
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
//    @Column(name = "deleted_at", updatable = false)
//    private LocalDateTime deletedAt;
//}
