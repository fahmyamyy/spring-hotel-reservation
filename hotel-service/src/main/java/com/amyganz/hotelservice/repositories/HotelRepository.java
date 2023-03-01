package com.amyganz.hotelservice.repositories;

import com.amyganz.hotelservice.entities.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Page<Hotel> findByNameContainsAndDeletedAtIsNull(Pageable pageable, String name);
    Page<Hotel> findHotelsByDeletedAtIsNull(Pageable pageable);
    Optional<Hotel> findByNameEqualsAndDeletedAtIsNull(String name);
    Page<Hotel> findByLocationContainsAndDeletedAtIsNull(Pageable pageable, String location);
    Page<Hotel> findByClsEqualsAndDeletedAtIsNull(Pageable pageable, Integer cls);
}
