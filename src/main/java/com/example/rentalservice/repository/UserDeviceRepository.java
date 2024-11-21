package com.example.rentalservice.repository;

import com.example.rentalservice.entity.UserDevice;
import com.example.rentalservice.entity.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, String> {
    Optional<UserDevice> findFirstByUsernameAndDevice(String username, String device);

    List<UserDevice> findAllByUsername(String username);
}