package com.example.rentalservice.model.search.res;

import com.example.rentalservice.common.DateUtils;
import com.example.rentalservice.common.RepositoryUtils;
import com.example.rentalservice.enums.BillStatusEnum;
import lombok.*;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BillSearchResDTO {
    private String billId;
    private String billCode;
    private Integer month;
    private Integer year;
    private String status;
    private String statusName;
    private String houseName;
    private String roomName;
    private Long price;
    private String createDate;

    public BillSearchResDTO(Object[] x) {
        AtomicInteger i = new AtomicInteger(0);
        this.billId = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.billCode = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.month = RepositoryUtils.setValueForField(Integer.class, x[i.getAndIncrement()]);
        this.year = RepositoryUtils.setValueForField(Integer.class, x[i.getAndIncrement()]);
        this.status = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.statusName = BillStatusEnum.from(status).getTitle();
        this.houseName = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.roomName = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.price = RepositoryUtils.setValueForField(Long.class, x[i.getAndIncrement()]);
        this.createDate = DateUtils.toStr(RepositoryUtils.setValueForField(LocalDate.class, x[i.getAndIncrement()]), DateUtils.F_DDMMYYYY);
    }
}
