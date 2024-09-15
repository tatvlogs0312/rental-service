package com.example.rentalservice.repository;

import com.example.rentalservice.entity.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomImageRepository extends JpaRepository<RoomImage, String> {

    List<RoomImage> findAllByRoomId(String roomId);
}