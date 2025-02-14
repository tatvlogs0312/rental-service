package com.example.rentalservice.repository;

import com.example.rentalservice.entity.Post;
import com.example.rentalservice.model.post.ILessorPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {

    Page<Post> findAllByLessorOrderByCreateTimeDesc(String lessor, Pageable pageable);

    @Query(value = """
            select p.id           as id,
                   p.title        as title,
                   p.number_watch as numberWatch,
                   p.create_time  as createTime,
                   rit.url        as firstImage,
                   cpi.num_image  as numImage
            from post p
                     join (select id, post_id, url, row_number() over (PARTITION BY post_id order by id) as num from post_image) rit
                          on rit.post_id = p.id and rit.num = 1
                     join (select pi.post_id, count(*) as num_image from post_image pi group by pi.post_id) cpi
                          on cpi.post_id = p.id
            where 1 = 1
              and p.lessor = :lessor
            order by p.create_time desc;
            """, countQuery = """
            select count(*)
            from post p
                     join (select id, post_id, url, row_number() over (PARTITION BY post_id order by id) as num from post_image) rit
                          on rit.post_id = p.id and rit.num = 1
                     join (select pi.post_id, count(*) as num_image from post_image pi group by pi.post_id) cpi
                          on cpi.post_id = p.id
            where 1 = 1
              and p.lessor = :lessor
            """,nativeQuery = true)
    Page<ILessorPost> findAllByLessor(String lessor, Pageable pageable);

    @Query(nativeQuery = true, value = """
                select p.id              as postId,
                       p.title           as tilte,
                       p.position_detail as positionDetail,
                       p.province        as province,
                       p.district        as district,
                       p.ward            as ward,
                       rt.code           as typeCode,
                       rt.name           as typeName,
                       p.price           as price,
                       p.create_time     as createTime,
                       rit.url           as firstImage
                from post p
                         join room_type rt on rt.id = p.room_type_id
                         join (select id, post_id, url, row_number() over (PARTITION BY post_id order by id) as num from post_image) rit
                              on rit.post_id = p.id and rit.num = 1
                where 1 = 1
                  and (rt.id = :roomTypeId or :roomTypeId is null)
                  and (lower(unaccent(p.position_detail)) like ('%' || lower(unaccent(:position)) || '%')
                    or lower(unaccent(p.ward)) like ('%' || lower(unaccent(:position)) || '%')
                    or lower(unaccent(p.district)) like ('%' || lower(unaccent(:position)) || '%')
                    or lower(unaccent(p.province)) like ('%' || lower(unaccent(:position)) || '%'))
                  and lower(unaccent(p.ward)) like ('%' || lower(unaccent(:ward)) || '%')
                  and lower(unaccent(p.district)) like ('%' || lower(unaccent(:district)) || '%')
                  and lower(unaccent(p.province)) like ('%' || lower(unaccent(:province)) || '%')
                  and (p.price between :priceFrom and :priceTo)
                order by p.create_time desc;
            """,
            countQuery = """
                    select count(*)
                    from post p
                         join room_type rt on rt.id = p.room_type_id
                         join (select id, post_id, url, row_number() over (PARTITION BY post_id order by id) as num from post_image) rit
                              on rit.post_id = p.id and rit.num = 1
                    where 1 = 1
                      and (rt.id = :roomTypeId or :roomTypeId is null)
                      and (lower(unaccent(p.position_detail)) like ('%' || lower(unaccent(:position)) || '%')
                        or lower(unaccent(p.ward)) like ('%' || lower(unaccent(:position)) || '%')
                        or lower(unaccent(p.district)) like ('%' || lower(unaccent(:position)) || '%')
                        or lower(unaccent(p.province)) like ('%' || lower(unaccent(:position)) || '%'))
                      and lower(unaccent(p.ward)) like ('%' || lower(unaccent(:ward)) || '%')
                      and lower(unaccent(p.district)) like ('%' || lower(unaccent(:district)) || '%')
                      and lower(unaccent(p.province)) like ('%' || lower(unaccent(:province)) || '%')
                      and (p.price between :priceFrom and :priceTo);
                    """)
    Page<Object[]> findAllByCondition(String roomTypeId, String position, String ward, String district, String province, Long priceFrom, Long priceTo, Pageable pageable);

    @Query(value = """
                    select p.id              as postId,
                           p.title           as tilte,
                           p.position_detail as positionDetail,
                           p.province        as province,
                           p.district        as district,
                           p.ward            as ward,
                           rt.code           as typeCode,
                           rt.name           as typeName,
                           p.price           as price,
                           p.create_time     as createTime,
                           rit.url           as firstImage
                    from post p
                             join room_type rt on rt.id = p.room_type_id
                             join (select id, post_id, url, row_number() over (PARTITION BY post_id order by id) as num from post_image) rit
                                  on rit.post_id = p.id and rit.num = 1
                    where 1 = 1
                      and ((:roomTypeIds is null or p.room_type_id in (:roomTypeIds))
                      and (:positions is null or p.ward in (:positions))
                      and (p.price between :priceFrom and :priceTo))
                      and (:ignore is null or p.id <> :ignore)
                    order by p.number_watch desc;
            """, countQuery = """
            select count(*)
            from post p
                     join room_type rt on rt.id = p.room_type_id
                     join (select id, post_id, url, row_number() over (PARTITION BY post_id order by id) as num from post_image) rit
                          on rit.post_id = p.id and rit.num = 1
            where 1 = 1
                      and ((:roomTypeIds is null or p.room_type_id in (:roomTypeIds))
                      and (:positions is null or p.ward in (:positions))
                      and (p.price between :priceFrom and :priceTo))
                      and (:ignore is null or p.id <> :ignore)
            """, nativeQuery = true)
    Page<Object[]> findAllRecommend(String ignore, List<String> roomTypeIds, List<String> positions, Long priceFrom, Long priceTo, Pageable pageable);
}