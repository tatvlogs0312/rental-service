package com.example.rentalservice.repository;

import com.example.rentalservice.entity.ContractUtility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractUtilityRepository extends JpaRepository<ContractUtility, String> {
}