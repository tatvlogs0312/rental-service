package com.example.rentalservice.entity;

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
@Entity(name = "bill")
@Table(name = "bill")
public class Bill {

    @Id
    private String id;

    private String contractId;

    private Integer month;

    private Integer year;

    private LocalDate createDate;

    private LocalDate paymentDate;

    private String status;

    private Boolean isRentContinue;
}
