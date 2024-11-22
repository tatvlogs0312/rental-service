package com.example.rentalservice.repository;

import com.example.rentalservice.entity.ContractUtility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractUtilityRepository extends JpaRepository<ContractUtility, String> {

    List<ContractUtility> findAllByContractId(String contractId);
}