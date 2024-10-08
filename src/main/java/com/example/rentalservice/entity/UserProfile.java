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
@Table(name = "user_profile")
@Entity(name = "user_profile")
public class UserProfile {

    @Id
    private String id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String identityNumber;

    private String phoneNumber;

    private String email;

    private String role;

    private String status;
}
