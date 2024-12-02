package com.example.rentalservice.model.bill;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BillDTO {
    private String billId;
    private String billCode;
    private Integer month;
    private Integer year;
    private String status;
    private Long moneyPayment;
    private String createDate;
    private String paymentDate;
    private String contractId;
    private String contractCode;
    private String houseId;
    private String houseName;
    private String roomId;
    private String roomName;
    private Boolean isRentContinue;
    private Long rentPriceNextMonth;
    List<BillDetailDTO> details;
}
