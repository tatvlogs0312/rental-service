package com.example.rentalservice.repository;

import com.example.rentalservice.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, String> {

    @Query(value = "select nextval('seq_bill')", nativeQuery = true)
    Long getSeqBill();

    List<Bill> findAllByContractIdAndMonthAndYearAndStatusIn(String contractId, Integer month, Integer year, List<String> status);
}