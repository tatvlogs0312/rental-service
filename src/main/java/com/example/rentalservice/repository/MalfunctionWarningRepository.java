package com.example.rentalservice.repository;

import com.example.rentalservice.entity.MalfunctionWarning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MalfunctionWarningRepository extends JpaRepository<MalfunctionWarning, String> {
}