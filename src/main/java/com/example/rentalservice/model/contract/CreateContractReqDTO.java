package com.example.rentalservice.model.contract;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateContractReqDTO {
    private String tenant;
    private String houseId;
    private String roomId;
    private Long price;
    private String startDate;
    private List<CreateContractUtilityReqDTO> utilities;
}
