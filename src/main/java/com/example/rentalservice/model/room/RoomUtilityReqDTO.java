package com.example.rentalservice.model.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomUtilityReqDTO {
    private String id;
    private String roomId;
    private String utilityId;
    private Long price;
    private String unit;
}
