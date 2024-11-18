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
@Entity(name = "house")
@Table(name = "house")
public class House {

    @Id
    private String id;

    @Column(columnDefinition = "TEXT")
    private String houseName;

    @Column(columnDefinition = "TEXT")
    private String province;

    @Column(columnDefinition = "TEXT")
    private String district;

    @Column(columnDefinition = "TEXT")
    private String ward;

    @Column(columnDefinition = "TEXT")
    private String positionDetail;

    private String lessor;

    private Boolean deleted;
}
