package com.example.rentalservice.repository;

import com.example.rentalservice.entity.RoomUtility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RoomUtilityRepository extends JpaRepository<RoomUtility, String> {

    Optional<RoomUtility> findFirstByRoomIdAndUtilityId(String roomId, String utilityId);

    Optional<RoomUtility> findFirstByRoomIdAndUtilityIdAndIsActive(String roomId, String utilityId, Boolean isActive);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = """
            update room_utility
            set is_active = :isActive
            where room_id = :roomId;
    """)
    void updateIsActiveById(String roomId, Boolean isActive);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = """
            update room_utility
            set is_active = false
            where room_id = :roomId and (is_active = true or is_active is null);
    """)
    void updateInactiveByRoomId(String roomId);
}