package com.example.rentalservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "booking")
@Table(name = "booking")
public class Booking {

    @Id
    private String id;

    private String tenant;

    private String roomId;

    private String status;

    private LocalDateTime dateBooking;

    private LocalDateTime dateWatch;

    @Column(columnDefinition = "TEXT")
    private String bookingMessage;
}
