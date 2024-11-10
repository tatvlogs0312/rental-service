package com.example.rentalservice.model.house;

import lombok.*;

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
}
