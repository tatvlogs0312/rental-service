package com.example.rentalservice.entity;

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
@Entity(name = "contract")
@Table(name = "contract")
public class Contract {

    @Id
    private String id;

    private String contractCode;

    private String lessor;

    private String tenant;

    private String houseId;

    private String roomId;

    private LocalDate createdDate;

    private LocalDateTime createdTime;

    private LocalDate effectDate;

    private LocalDate endDate;

    private LocalDateTime signedTime;

    private Long actualPrice;

    private String status;

    private String otp;

    private Integer numberOtp;

    private LocalDateTime otpExpiredTime;
}
