package com.example.rentalservice.repository;

import com.example.rentalservice.entity.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<House, String> {

    Page<House> findAllByLessorAndDeleted(String lessor, Boolean deleted, Pageable pageable);
}