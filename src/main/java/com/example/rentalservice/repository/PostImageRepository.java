package com.example.rentalservice.repository;

import com.example.rentalservice.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, String> {
    List<PostImage> findAllByPostId(String postId);
}