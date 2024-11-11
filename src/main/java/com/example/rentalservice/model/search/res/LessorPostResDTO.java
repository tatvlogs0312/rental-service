package com.example.rentalservice.model.search.res;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LessorPostResDTO {
    private String id;
    private String title;
    private String createTime;
    private Long numberWatch;
}
