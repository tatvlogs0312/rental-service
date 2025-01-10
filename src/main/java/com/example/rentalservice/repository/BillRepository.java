package com.example.rentalservice.repository;

import com.example.rentalservice.entity.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, String> {

    @Query(value = "select nextval('seq_bill')", nativeQuery = true)
    Long getSeqBill();

    List<Bill> findAllByContractIdAndMonthAndYearAndStatusIn(String contractId, Integer month, Integer year, List<String> status);

    Long countAllByLessorAndStatus(String lessor, String status);

    @Query(value = """
            select b.id         as billId,
                   b.bill_code  as billCode,
                   b.month      as month,
                   b.year       as year,
                   b.status     as status,
                   h.house_name as houseName,
                   r.room_name  as roomName,
                   b.number_payed as price,
                   b.create_date  as createDate
            from bill b
                     join contract c on b.contract_id = c.id
                     join house h on c.house_id = h.id
                     join room r on c.room_id = r.id
            where 1 = 1
              and (:year is null or b.year = :year)
              and (:month is null or b.month = :month)
              and (:status is null or b.status = :status)
              and (:lessor is null or b.lessor = :lessor)
              and (:tenant is null or b.tenant = :tenant)
            """, nativeQuery = true)
    Page<Object[]> findAllByMonthAndYear(Integer month, Integer year, String status, String lessor, String tenant, Pageable pageable);

    @Query(value = """
            select b.id                                 as billId,
                   b.bill_code                          as billCode,
                   b.month                              as month,
                   b.year                               as year,
                   b.status                             as billStatus,
                   b.number_payed                       as moneyPayment,
                   b.create_date                        as createDate,
                   b.payment_date                       as paymentDate,
                   c.id                                 as contractId,
                   c.contract_code                      as contractCode,
                   h.id                                 as houseId,
                   h.house_name                         as houseName,
                   r.id                                 as roomId,
                   r.room_name                          as roomName,
                   b.is_rent_continue                   as isRentContinue,
                   up.first_name || ' ' || up.last_name as tenantFullName,
                   up.phone_number                      as tenantPhoneNumber
            from bill b
                     join contract c on b.contract_id = c.id
                     join house h on c.house_id = h.id
                     join room r on c.room_id = r.id
                     join user_profile up on c.tenant = up.username
            where b.id = :id
            """, nativeQuery = true)
    List<Object[]> findBillById(String id);

    @Query(value = """
            WITH months AS (SELECT 1 AS month
                            UNION ALL
                            SELECT 2
                            UNION ALL
                            SELECT 3
                            UNION ALL
                            SELECT 4
                            UNION ALL
                            SELECT 5
                            UNION ALL
                            SELECT 6
                            UNION ALL
                            SELECT 7
                            UNION ALL
                            SELECT 8
                            UNION ALL
                            SELECT 9
                            UNION ALL
                            SELECT 10
                            UNION ALL
                            SELECT 11
                            UNION ALL
                            SELECT 12)
            SELECT m.month, COALESCE(SUM(b.number_payed), 0)::bigint AS total
            FROM months m LEFT JOIN bill b ON m.month = b.month
                                                  AND b.lessor = :lessor
                                                  AND b.status = 'PAYED'
                                                  AND b.year = :year
            GROUP BY m.month ORDER BY m.month
            """, nativeQuery = true)
    List<Object[]> getBillInYear(String lessor, Integer year);
}