package com.example.rentalservice.model.dashboard;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DashboardDetailDTO {
    private int month;
    private int year;
    private Long contractDraft = 0L;
    private Long contractPending = 0L;
    private Long billDraft = 0L;
    private Long billPending = 0L;
    private Long warningPending = 0L;
}
