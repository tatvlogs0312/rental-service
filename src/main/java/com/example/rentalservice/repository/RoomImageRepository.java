package com.example.rentalservice.repository;

import com.example.rentalservice.entity.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomImageRepository extends JpaRepository<RoomImage, String> {

    Optional<RoomImage> findFirstByUrl(String url);

    List<RoomImage> findAllByRoomId(String roomId);

    @Query(value = "select count(*) + 1 from room_image where room_id = :roomId", nativeQuery = true)
    Long getNextIndexUpload(String roomId);
}