package com.example.rentalservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "room")
@Table(name = "room")
public class Room {

    @Id
    private String id;

    private String roomCode;

    private String lessor;

    private Long price;

    private Integer acreage;

    private Integer numberOfRom;

    private String roomStatus;

    @Column(columnDefinition = "TEXT")
    private String roomName;

    private String houseId;

    private Boolean deleted;

    private String floor;
}
