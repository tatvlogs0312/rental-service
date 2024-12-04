package com.example.rentalservice.entity;

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
@Entity(name = "malfunction_warning_image")
@Table(name = "malfunction_warning_image")
public class MalfunctionWarningImage {
    @Id
    private String id;

    private String malfunctionWarningId;

    private String url;
}
