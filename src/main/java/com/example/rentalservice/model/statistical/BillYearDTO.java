package com.example.rentalservice.model.statistical;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BillYearDTO {
    private Integer month;
    private Long total;
}
