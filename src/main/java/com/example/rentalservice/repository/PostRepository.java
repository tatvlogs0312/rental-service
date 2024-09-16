package com.example.rentalservice.repository;

import com.example.rentalservice.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {

    @Query("select (count(p) > 0) from post p where p.roomId = ?1")
    Boolean existsAllByRoomId(String roomId);

    @Query(nativeQuery = true, value = """
                select p.id      as postId,
                       p.title   as tile,
                       p.content as content,
                       rp.detail as positionDetail,
                       rt.code   as typeCode,
                       rt.name   as typeName
                from post p
                         join room r on r.id = p.room_id
                         join room_type rt on rt.id = r.room_type_id
                         join room_position rp on r.id = rp.room_id
                where 1 = 1
                  and (rt.id = :roomTypeId or :roomTypeId is null)
                  and (lower(unaccent(rp.detail)) like ('%' || lower(unaccent(:position)) || '%')
                    or lower(unaccent(rp.ward)) like ('%' || lower(unaccent(:position)) || '%')
                    or lower(unaccent(rp.district)) like ('%' || lower(unaccent(:position)) || '%')
                    or lower(unaccent(rp.province)) like ('%' || lower(unaccent(:position)) || '%'))
                  and lower(unaccent(rp.ward)) like ('%' || lower(unaccent(:ward)) || '%')
                  and lower(unaccent(rp.district)) like ('%' || lower(unaccent(:district)) || '%')
                  and lower(unaccent(rp.province)) like ('%' || lower(unaccent(:province)) || '%')
                order by p.create_time desc;
            """)
    Page<Object[]> findAllByCondition(String roomTypeId, String position, String ward, String district, String province);
}