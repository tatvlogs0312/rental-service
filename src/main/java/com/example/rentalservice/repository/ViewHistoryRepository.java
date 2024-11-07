package com.example.rentalservice.repository;

import com.example.rentalservice.entity.ViewHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViewHistoryRepository extends JpaRepository<ViewHistory, String> {

    Optional<ViewHistory> findFirstByUsernameAndPostId(String username, String postId);
}