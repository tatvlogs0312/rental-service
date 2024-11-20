package com.example.rentalservice.model.contract;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ContractSignReqDTO {
    private String contractId;
    private String otp;
}
