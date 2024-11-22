package com.example.rentalservice.model.contract;

import com.example.rentalservice.model.room.detail.PositionDTO;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ContractDetailDTO {
    private String contractId;
    private String contractCode;
    private String contractStatusCode;
    private String contractStatusName;
    private String statusMessage;
    private String createdTime;
    private String startDate;
    private String endDate;
    private String signTime;
    private Long price;

    private String lessorFirstName;
    private String lessorLastName;
    private String lessorPhoneNumber;

    private String tenantFirstName;
    private String tenantLastName;
    private String tenantPhoneNumber;

    private String houseId;
    private String houseName;
    private String roomId;
    private String roomName;
    private PositionDTO position;

    private List<ContractUtilityDTO> utilities;
}
