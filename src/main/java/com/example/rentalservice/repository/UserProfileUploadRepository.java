package com.example.rentalservice.repository;

import com.example.rentalservice.entity.UserProfileUpload;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileUploadRepository extends JpaRepository<UserProfileUpload, String> {
    List<UserProfileUpload> findAllByUsername(String username);

    Optional<UserProfileUpload> findFirstByUsernameAndType(String username, String type);
    Optional<UserProfileUpload> findFirstByUsernameAndId(String username, String id);
}