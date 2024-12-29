package com.example.rentalservice.model.post;

import java.time.LocalDateTime;

public interface ILessorPost {
    String getId();
    String getTitle();
    Long getNumberWatch();
    LocalDateTime getCreateTime();
    String getFirstImage();
    Long getNumImage();
}
