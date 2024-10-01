package com.example.rentalservice.repository;

import com.example.rentalservice.entity.RoomPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomPositionRepository extends JpaRepository<RoomPosition, String> {

    Optional<RoomPosition> findFirstByRoomId(String roomId);
}