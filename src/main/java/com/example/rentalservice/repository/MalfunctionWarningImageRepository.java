package com.example.rentalservice.repository;

import com.example.rentalservice.entity.MalfunctionWarningImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MalfunctionWarningImageRepository extends JpaRepository<MalfunctionWarningImage, String> {

    List<MalfunctionWarningImage> findAllByMalfunctionWarningId(String warningId);
}