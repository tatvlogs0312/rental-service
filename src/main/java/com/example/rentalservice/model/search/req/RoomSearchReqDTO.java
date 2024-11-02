package com.example.rentalservice.model.search.req;

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
    private String keyword;
    private int page;
    private int size;
}
