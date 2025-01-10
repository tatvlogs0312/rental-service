package com.example.rentalservice.model.statistical;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BillStatisticalDTO {
    private Long numberDraft;
    private Long numberPending;
    private Long numberPaid;
}
