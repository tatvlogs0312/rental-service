package com.example.rentalservice.repository;

import com.example.rentalservice.entity.UserNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, String> {
    Long countAllByUsernameAndIsRead(String username, Boolean isRead);

    Page<UserNotification> findAllByUsername(String username, Pageable pageable);

    Page<UserNotification> findAllByUsernameOrderByTimeSendDesc(String username, Pageable pageable);
}