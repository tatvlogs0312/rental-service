package com.example.rentalservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "view_history")
@Entity(name = "view_history")
public class ViewHistory {

    @Id
    private String id;

    private String postId;

    private String roomId;

    private String roomType;

    private String position;

    private Long price;

    private String username;

    private LocalDateTime timeView;
}
