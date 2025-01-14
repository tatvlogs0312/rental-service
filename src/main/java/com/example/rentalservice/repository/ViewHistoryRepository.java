package com.example.rentalservice.repository;

import com.example.rentalservice.entity.ViewHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ViewHistoryRepository extends JpaRepository<ViewHistory, String> {

    Optional<ViewHistory> findFirstByUsernameAndPostId(String username, String postId);

    @Query(value = "select * from view_history where username = :username order by time_view desc limit 3", nativeQuery = true)
    List<ViewHistory> findAllByUsername(String username);
}