package com.example.rentalservice.repository;

import com.example.rentalservice.entity.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, String> {

    @Query(nativeQuery = true, value = """
            select *
            from room_type
            where 1 = 1
              and (lower(unaccent(name)) like ('%' || lower(unaccent(:name)) || '%'));
            """)
    Page<RoomType> searchByName(String name, Pageable pageable);
}