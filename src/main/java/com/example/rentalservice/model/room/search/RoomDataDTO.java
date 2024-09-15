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

    public RoomDataDTO (IRoomData iRoomData) {
        this.roomId = iRoomData.getRoomId();
        this.roomCode = iRoomData.getRoomCode();
        this.roomStatus = iRoomData.getRoomStatus();
        this.type = iRoomData.getType();
        this.typeName = iRoomData.getTypeName();
        this.positionDetail = iRoomData.getPositionDetail();
        this.province = iRoomData.getProvince();
        this. district = iRoomData.getDistrict();
        this.ward = iRoomData.getWard();
    }
}
