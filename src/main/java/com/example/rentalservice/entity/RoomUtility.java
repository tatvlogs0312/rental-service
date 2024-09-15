package com.example.rentalservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "room_utility")
@Table(name = "room_utility")
public class RoomUtility {

    @Id
    private String id;

    private String roomId;

    private String utilityId;

    private Long price;

    private String unit;

    private Boolean isActive;
}
