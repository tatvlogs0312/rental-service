package com.example.rentalservice.repository;

import com.example.rentalservice.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {

    @Query("select (count(p) > 0) from post p where p.roomId = ?1")
    Boolean existsAllByRoomId(String roomId);
}