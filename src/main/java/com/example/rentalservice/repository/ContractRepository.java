package com.example.rentalservice.repository;

import com.example.rentalservice.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, String> {

    @Query(value = "select nextval('seq_contract')", nativeQuery = true)
    Long getSeqContract();
}