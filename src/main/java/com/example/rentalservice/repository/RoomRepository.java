package com.example.rentalservice.repository;

import com.example.rentalservice.entity.Room;
import com.example.rentalservice.model.room.search.IRoomData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

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
                  and (r.room_status = :status or :status is null)
                  and (rt.id in (:roomTypeIds) or :roomTypeIds is null)
                  and (unaccent(rp.detail) like ('%' || unaccent(:position) || '%')
                    or unaccent(rp.ward) like ('%' || unaccent(:position) || '%')
                    or unaccent(rp.district) like ('%' || unaccent(:position) || '%')
                    or unaccent(rp.province) like ('%' || unaccent(:position) || '%'))
                  and unaccent(rp.ward) like ('%' || unaccent(:ward) || '%')
                  and unaccent(rp.district) like ('%' || unaccent(:district) || '%')
                  and unaccent(rp.province) like ('%' || unaccent(:province) || '%')
                order by r.room_code;
            """)
    Page<IRoomData> findAllByCondition(String status, List<String> roomTypeIds, String position, String ward,
                                       String district, String province, Pageable pageable);
}