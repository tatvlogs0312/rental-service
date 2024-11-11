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
@Entity(name = "post")
@Table(name = "post")
public class Post {

    @Id
    private String id;

    private String lessor;

    private String roomId;

    private Long numberWatch;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createTime;

    @Column(columnDefinition = "TEXT")
    private String province;

    @Column(columnDefinition = "TEXT")
    private String district;

    @Column(columnDefinition = "TEXT")
    private String ward;

    @Column(columnDefinition = "TEXT")
    private String positionDetail;

    private Long price;

    private Integer acreage;

    private Integer numberOfRoom;

    private String roomTypeId;
}
