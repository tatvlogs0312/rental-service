package com.example.rentalservice.model.contract;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ContractEndReqDTO {
    private String contractId;
    private String dateEnd;
    private String reason;
}
