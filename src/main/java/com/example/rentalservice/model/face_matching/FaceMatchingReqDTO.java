package com.example.rentalservice.model.face_matching;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FaceMatchingReqDTO {
    private File face1;
    private File face2;
    private Double score;
}
