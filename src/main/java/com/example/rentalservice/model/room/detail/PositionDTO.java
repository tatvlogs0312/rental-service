package com.example.rentalservice.model.room.detail;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PositionDTO {
    private String detail;
    private String ward;
    private String district;
    private String province;
}
