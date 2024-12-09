package com.example.rentalservice.repository;

import com.example.rentalservice.entity.MalfunctionWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MalfunctionWarningRepository extends JpaRepository<MalfunctionWarning, String> {

    @Query(value = """
            select mw.id                                as id,
                   mw.title                             as title,
                   mw.content                           as content,
                   mw.create_time                       as createTime,
                   mw.status                            as status,
                   h.id                                 as houseId,
                   h.house_name                         as houseName,
                   r.id                                 as roomId,
                   r.room_name                          as roomName,
                   ul.first_name || ' ' || ul.last_name as lessorFullName,
                   ul.phone_number                      as lessorPhoneNumber,
                   ut.first_name || ' ' || ut.last_name as tenantFullName,
                   ut.phone_number                      as tenantPhoneNumber
            from malfunction_warning mw
                     join house h on mw.house_id = h.id
                     join room r on mw.room_id = r.id
                     join user_profile ul on mw.lessor = ul.username
                     join user_profile ut on mw.tenant = ut.username
            where 1 = 1
              and (:status is null or mw.status = :status)
              and (:lessor is null or mw.lessor = :lessor)
              and (:tenant is null or mw.tenant = :tenant)
            order by mw.create_time desc;
            """, nativeQuery = true)
    Page<Object[]> search(String status, String tenant, String lessor, Pageable pageable);
}