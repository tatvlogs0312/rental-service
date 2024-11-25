package com.example.rentalservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "bill")
@Table(name = "bill")
public class Bill {

    @Id
    private String id;

    private String billCode;

    private String contractId;

    private Integer month;

    private Integer year;

    private LocalDate createDate;

    private LocalDate paymentDate;

    private String status;

    private Boolean isRentContinue;

    private Long numberPayed;

    private String lessor;

    private String tenant;
}
