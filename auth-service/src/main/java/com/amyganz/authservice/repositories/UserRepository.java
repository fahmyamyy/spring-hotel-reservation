package com.amyganz.authservice.repositories;

import com.amyganz.authservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
//    Optional<User> findByUsernameEqualsAndOtpEquals(String username, String otp);
    Optional<User> findByEmail(String email);
}
