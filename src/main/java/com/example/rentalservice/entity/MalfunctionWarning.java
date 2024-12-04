package com.example.rentalservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "malfunction_warning")
@Table(name = "malfunction_warning")
public class MalfunctionWarning {

    @Id
    private String id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createTime;

    private String lessor;

    private String tenant;

    private String houseId;

    private String roomId;
}
