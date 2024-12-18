package com.example.rentalservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "user_notification")
@Table(name = "user_notification")
public class UserNotification {
    @Id
    private String id;

    private String username;

    @Column(columnDefinition = "TEXT")
    private String data;

    private String title;

    private String content;

    private Boolean isRead;

    private LocalDateTime timeSend;
}
