package com.example.rentalservice.repository;

import com.example.rentalservice.entity.BillDetail;
import com.example.rentalservice.model.bill.IBillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, String> {

    @Query(value = """
            select bd.id          as billDetailId,
                   u.id           as utilityId,
                   u.name         as utilityName,
                   bd.number_used as numberUsed,
                   cu.price       as utilityPrice,
                   cu.unit        as utilityUnit
            from bill_detail bd
                     join bill b on bd.bill_id = b.id
                     join contract_utility cu on cu.contract_id = b.contract_id
                     join utility u on u.id = bd.utility_id
            where bd.bill_id = :billId
            """, nativeQuery = true)
    List<IBillDetail> findAllByBillId(String billId);
}