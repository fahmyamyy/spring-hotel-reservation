package com.amyganz.userservice.repositories;

import com.amyganz.userservice.entities.User;
import com.amyganz.userservice.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findUserInfoByUsers(User userId);
    Optional<UserInfo> findUserInfoByHotel(Long hotelId);
}
