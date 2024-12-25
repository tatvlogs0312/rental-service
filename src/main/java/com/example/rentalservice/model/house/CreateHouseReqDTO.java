package com.example.rentalservice.model.house;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateHouseReqDTO {
    private String houseName;
    private String positionDetail;
    private String ward;
    private String district;
    private String province;
    private MultipartFile image;
}
