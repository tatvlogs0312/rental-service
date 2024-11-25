package com.example.rentalservice.model.bill;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateBillDetailReqDTO {
    private String utilityId;
    private Integer numberUsed;
}
