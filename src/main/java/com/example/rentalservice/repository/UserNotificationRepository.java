package com.example.rentalservice.repository;

import com.example.rentalservice.entity.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, String> {
    Optional<UserNotification> findFirstByUsernameAndDevice(String username, String device);
}