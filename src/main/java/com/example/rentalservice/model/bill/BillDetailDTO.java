package com.example.rentalservice.model.bill;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BillDetailDTO {
    private String billId;
    private String billCode;
}
