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
    private String houseId;
    private String roomTypeId;
    private String roomName;
    private Long price;
    private Integer acreage;
    private Integer numberOfRoom;
}
