package com.example.rentalservice.model.contract;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ContractUtilityDTO {
    private String utilityId;
    private String utilityName;
    private Long utilityPrice;
    private String utilityUnit;
}
