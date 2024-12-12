package com.example.rentalservice.model.contract;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ContractEndReqDTO {
    private String contractId;
    private String dateEnd;
    private LocalDate endDate;
    private String reason;
}
