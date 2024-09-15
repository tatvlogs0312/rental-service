package com.example.rentalservice.model.face_matching;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FaceMatchingResDTO {
    private Boolean verified;
    private Double distance;
    private Double threshold;
    private Double time;
}
