package com.example.rentalservice.repository;

import com.example.rentalservice.entity.Utilities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilitiesRepository extends JpaRepository<Utilities, String> {

}