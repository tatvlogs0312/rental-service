package com.example.rentalservice.model.search.res;

import com.example.rentalservice.common.DateUtils;
import com.example.rentalservice.common.RepositoryUtils;
import lombok.*;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WarningSearchResDTO {
    private String id;
    private String title;
    private String content;
    private String createTime;
    private LocalDateTime createTimeV2;
    private String status;
    private String houseId;
    private String houseName;
    private String roomId;
    private String roomName;
    private String lessorFullName;
    private String lessorPhoneNumber;
    private String tenantFullName;
    private String tenantPhoneNumber;

    public WarningSearchResDTO(Object[] x) {
        AtomicInteger i = new AtomicInteger(0);
        this.id = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.title = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.content = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.createTimeV2 = RepositoryUtils.setValueForField(LocalDateTime.class, x[i.getAndIncrement()]);
        this.createTime = DateUtils.toStr(createTimeV2, DateUtils.F_HHMMSSDDMMYYYY);
        this.status = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.houseId = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.houseName = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.roomId = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.roomName = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.lessorFullName = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.lessorPhoneNumber = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.tenantFullName = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.tenantPhoneNumber = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
    }
}
