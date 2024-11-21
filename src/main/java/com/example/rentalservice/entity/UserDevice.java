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
@Table(name = "user_device")
@Entity(name = "user_device")
public class UserDevice {

    @Id
    private String id;

    private String username;

    @Column(columnDefinition = "TEXT")
    private String device;
}
