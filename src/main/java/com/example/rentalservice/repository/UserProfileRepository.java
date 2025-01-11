package com.example.rentalservice.repository;

import com.example.rentalservice.entity.UserProfile;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, String> {
    Optional<UserProfile> findFirstByUsername(String username);

    Optional<UserProfile> findFirstByUsernameAndRole(String username, String role);

    Boolean existsAllByEmail(String email);

    Boolean existsAllByPhoneNumber(String phoneNumber);

    @Query(value = """
            select *
            from user_profile
            where (email = :keyword
               or phone_number = :keyword
               or username = :keyword)
               and role = :role
            limit 1;
            """, nativeQuery = true)
    Optional<UserProfile> findByKeyword(String keyword, String role);

    @Query(value = "select (count(*) > 0) from user_profile where email = :email and username <> :username", nativeQuery = true)
    Boolean existOtherEmail(String email, String username);

    @Query(value = "select (count(*) > 0) from user_profile where phone_number = :phoneNumber and username <> :username", nativeQuery = true)
    Boolean existOtherPhoneNumber(String phoneNumber, String username);
}