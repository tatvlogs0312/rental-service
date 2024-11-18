package com.example.rentalservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "contract")
@Table(name = "contract")
public class Contract {

    @Id
    private String id;

    private String contractCode;

    private String tenant;

    private String houseId;

    private String roomId;

    private LocalDate effectDate;

    private LocalDate endDate;

    private Long actualPrice;

    private String step;

    private String status;
}
