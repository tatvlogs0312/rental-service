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
    private Long utilityPrice;
    private String utilityUnit;
    private Long utilityPayment;

    public BillDetailDTO(IBillDetail x) {
        this.detailId = x.getBillDetailId();
        this.utilityId = x.getUtilityId();
        this.utilityName = x.getUtilityName();
        this.numberUsed = x.getNumberUsed();
        this.utilityPrice = x.getUtilityPrice();
        this.utilityUnit = x.getUtilityUnit();
        this.utilityPayment = x.getUtilityPrice() * x.getNumberUsed();
    }
}
