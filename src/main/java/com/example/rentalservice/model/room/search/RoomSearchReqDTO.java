package com.example.rentalservice.model.room.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomSearchReqDTO {
    private String status;
    private String roomTypeId;
    private String position;
    private String ward;
    private String district;
    private String province;
    private int page;
    private int size;
}
