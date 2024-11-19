package com.example.rentalservice.model.contract;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateContractUtilityReqDTO {
    private String utilityId;
    private Long price;
    private String unit;
}
