package com.example.rentalservice.model.room.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoomDataDTO {
    private String roomId;
    private String roomCode;
    private String roomStatus;
    private String type;
    private String typeName;
    private String positionDetail;
    private String province;
    private String district;
    private String ward;
}
