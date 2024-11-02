package com.example.rentalservice.repository;

import com.example.rentalservice.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    @Query(value = """
            select b.id              as bookId,
                   r.id              as roomId,
                   r.room_code       as roomCode,
                   b.status          as bookStatus,
                   b.date_watch      as dateWatch,
                   b.booking_message as bookingMessage,
                   upl.first_name    as lessorFirstName,
                   upl.last_name     as lessorLastName,
                   upl.phone_number  as lessorPhoneNumber,
                   upt.first_name    as tenantFirstName,
                   upt.last_name     as tenantLastName,
                   upt.phone_number  as tenantPhoneNumber,
                   rp.detail     as position,
                   rp.ward       as ward,
                   rp.district   as district,
                   rp.province   as province
            from booking b
                     join room r on b.room_id = r.id
                     join room_position rp on r.id = rp.room_id
                     join user_profile upl on upl.username = r.lessor
                     join user_profile upt on upt.username = b.tenant
            where 1 = 1
              and (:lessor is null or r.lessor = :lessor)
              and (:tenant is null or b.tenant = :tenant)
              and (:status is null or b.status = :status)
            order by b.date_watch desc;
        """, nativeQuery = true)
    Page<Object[]> findAllBookByUser(String lessor, String tenant, String status, Pageable pageable);
}