package com.example.rentalservice.repository;

import com.example.rentalservice.entity.Room;
import com.example.rentalservice.model.room.IRoomData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

    Long countAllByHouseIdAndRoomStatusAndDeleted(String houseId, String roomStatus, Boolean deleted);

    Long countAllByRoomStatusAndLessorAndDeleted(String status, String lessor, Boolean deleted);

    List<Room> findAllByHouseIdIn(List<String> houseId);

    List<Room> findAllByHouseIdInAndDeleted(List<String> houseId, Boolean deleted);

    Page<Room> findAllByHouseIdAndDeleted(String id, Boolean deleted, Pageable pageable);

    @Query(value = """
            select * from room
            where deleted = :deleted
            and (room_status is null or room_status = :roomStatus)
            and (house_id is null or house_id = :houseId)
            """, nativeQuery = true)
    Page<Room> findAllByHouseIdAndRoomStatus(String houseId, String roomStatus, Boolean deleted, Pageable pageable);

    @Query(value = "select nextval('seq_room')", nativeQuery = true)
    Long getSeqRoomCode();

    @Query(nativeQuery = true, value = """
                select r.id          as roomId,
                       r.room_code   as roomCode,
                       r.room_status as roomStatus,
                       rt.code       as type,
                       rt.name       as typeName,
                       rp.detail     as positionDetail,
                       rp.province   as province,
                       rp.district   as district,
                       rp.ward       as ward
                from room r
                join room_type rt on r.room_type_id = rt.id
                join room_position rp on r.id = rp.room_id
                where 1 = 1
                  and r.lessor = :lessor
                  and (r.room_status = :status or :status is null)
                  and (rt.id = :roomTypeId or :roomTypeId is null)
                  and (lower(unaccent(rp.detail)) like ('%' || lower(unaccent(:position))  || '%')
                    or lower(unaccent(rp.ward))  like ('%' || lower(unaccent(:position)) || '%')
                    or lower(unaccent(rp.district))  like ('%' || lower(unaccent(:position)) || '%')
                    or lower(unaccent(rp.province))  like ('%' || lower(unaccent(:position)) || '%'))
                  and lower(unaccent(rp.ward)) like ('%' || lower(unaccent(:ward))  || '%')
                  and lower(unaccent(rp.district)) like ('%' || lower(unaccent(:district))  || '%')
                  and lower(unaccent(rp.province))  like ('%' || lower(unaccent(:province))  || '%')
                order by r.room_code;
            """)
    Page<IRoomData> findAllByCondition(String lessor, String status, String roomTypeId, String position, String ward,
                                       String district, String province, Pageable pageable);

    @Query(value = """
            select h.id                                 as houseId,
                   h.house_name                         as houseName,
                   r.id                                 as roomId,
                   r.room_name                          as roomName,
                   h.position_detail                    as housePositionDetail,
                   h.ward                               as houseWard,
                   h.district                           as houseDistrict,
                   h.province                           as houseProvince,
                   ul.first_name || ' ' || ul.last_name as lessorFullName,
                   ul.phone_number                      as lessorPhoneNumber,
                   ut.first_name || ' ' || ut.last_name as tenantFullName,
                   ut.phone_number                      as tenantPhoneNumber
            from contract c
                     join house h on c.house_id = h.id
                     join room r on c.room_id = r.id
                     join user_profile ul on c.lessor = ul.username
                     join user_profile ut on ut.username = c.tenant
            where 1 = 1
              and (:tenant is null or c.tenant = :tenant)
              and (:lessor is null or c.lessor = :lessor)
              and c.status = 'SIGNED';
            """, nativeQuery = true)
    List<Object[]> getRoomRented(String tenant, String lessor);

    @Transactional
    @Modifying
    @Query(value = "update room set deleted = true where house_id = :houseId", nativeQuery = true)
    void updateRoomDeleted(String houseId);
}