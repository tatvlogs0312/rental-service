package com.example.rentalservice.repository;

import com.example.rentalservice.entity.MalfunctionWarningImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MalfunctionWarningImageRepository extends JpaRepository<MalfunctionWarningImage, String> {
}