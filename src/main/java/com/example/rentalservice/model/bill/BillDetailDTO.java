package com.example.rentalservice.model.bill;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BillDetailDTO {
    private String detailId;
    private String utilityId;
    private String utilityName;
    private Integer numberUsed;
    private Long utilityPayment;
}
