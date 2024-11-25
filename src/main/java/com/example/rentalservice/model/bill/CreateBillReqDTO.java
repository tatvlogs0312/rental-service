package com.example.rentalservice.model.bill;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateBillReqDTO {
    private String contractId;
    private Integer month;
    private Integer year;
    private Boolean isRentContinue;
    private List<CreateBillDetailReqDTO> details;
}
