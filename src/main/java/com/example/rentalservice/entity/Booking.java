package com.example.rentalservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "booking")
@Table(name = "booking")
public class Booking {

    @Id
    private String id;

    private String tenant;

    private String roomId;

    private String status;

    private LocalDate dateBooking;

    @Column(columnDefinition = "TEXT")
    private String bookingMessage;
}
