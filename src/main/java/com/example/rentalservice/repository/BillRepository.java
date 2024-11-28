package com.example.rentalservice.repository;

import com.example.rentalservice.entity.Bill;
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

    @Query(value = """
            select b.id            as billId,
                   b.bill_code     as billCode,
                   b.month         as month,
                   b.year          as year,
                   b.status        as billStatus,
                   b.number_payed  as moneyPayment,
                   b.create_date   as createDate,
                   b.payment_date  as paymentDate,
                   c.id            as contractId,
                   c.contract_code as contractCode,
                   h.id            as houseId,
                   h.house_name    as houseName,
                   r.id            as roomId,
                   r.room_name     as roomName
            from bill b
                     join contract c on b.contract_id = c.id
                     join house h on c.house_id = h.id
                     join room r on c.room_id = r.id
            where b.id = :id
            """, nativeQuery = true)
    Optional<Objects[]> findBillById(String id);
}