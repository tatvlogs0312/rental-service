package com.example.rentalservice.model.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoomReqDTO {
    private String id;

    private String roomTypeId;

    private Long price;
    private Integer acreage;
    private Integer numberOfRoom;

    private String province;
    private String district;
    private String ward;
    private String positionDetail;
}
