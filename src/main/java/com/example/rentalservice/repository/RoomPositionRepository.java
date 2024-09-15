package com.example.rentalservice.repository;

import com.example.rentalservice.entity.RoomPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomPositionRepository extends JpaRepository<RoomPosition, String> {
}