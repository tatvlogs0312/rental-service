package com.example.rentalservice.model.statistical;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoomStatisticalDTO {
    private Long numberEmpty;
    private Long numberRented;
}
