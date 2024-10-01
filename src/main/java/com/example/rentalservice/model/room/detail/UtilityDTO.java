package com.example.rentalservice.model.room.detail;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UtilityDTO {
    private String utilityId;
    private String utilityName;
    private Long utilityPrice;
    private String utilityUnit;
}
