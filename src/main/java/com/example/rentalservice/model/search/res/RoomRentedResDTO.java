package com.example.rentalservice.model.search.res;

import com.example.rentalservice.common.RepositoryUtils;
import lombok.*;

import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoomRentedResDTO {
    private String houseId;
    private String houseName;
    private String roomId;
    private String roomName;
    private String housePositionDetail;
    private String houseWard;
    private String houseDistrict;
    private String houseProvince;
    private String lessorFullName;
    private String lessorPhoneNumber;
    private String tenantFullName;
    private String tenantPhoneNumber;

    public RoomRentedResDTO(Object[] x) {
        AtomicInteger i = new AtomicInteger(0);
        this.houseId = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.houseName = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.roomId = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.roomName = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.housePositionDetail = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.houseWard = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.houseDistrict = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.houseProvince = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.lessorFullName = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.lessorPhoneNumber = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.tenantFullName = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.tenantPhoneNumber = RepositoryUtils.setValueForField(String.class, x[i.get()]);
    }
}
