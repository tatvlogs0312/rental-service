package com.example.rentalservice.model.search.res;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostSearchResDTO {
    private String postId;
    private String title;
    private String positionDetail;
    private String province;
    private String district;
    private String ward;
    private String typeCode;
    private String typeName;
    private Long price;
    private LocalDateTime postTime;
    private String firstImage;
}
