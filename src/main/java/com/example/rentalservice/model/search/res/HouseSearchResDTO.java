package com.example.rentalservice.model.search.res;

import com.example.rentalservice.entity.House;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HouseSearchResDTO {
    private String id;
    private String houseName;
    private String province;
    private String district;
    private String ward;
    private String positionDetail;
    private Integer totalRoom;
    private Integer totalEmptyRoom;

    public HouseSearchResDTO(House house) {
        this.id = house.getId();
        this.houseName = house.getHouseName();
        this.province = house.getProvince();
        this.district = house.getDistrict();
        this.ward = house.getWard();
        this.positionDetail = house.getPositionDetail();
    }
}
