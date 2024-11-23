package com.example.rentalservice.model.contract;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ContractReqDTO {
    private String id;
    private String message;
}
