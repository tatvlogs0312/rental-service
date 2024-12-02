package com.example.rentalservice.model.search.res;

import com.example.rentalservice.common.DateUtils;
import com.example.rentalservice.common.RepositoryUtils;
import com.example.rentalservice.enums.ContractStatusEnum;
import lombok.*;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ContractSearchResDTO {
    private String contractId;
    private String contractCode;
    private String contractStatusCode;
    private String contractStatusName;
    private String createdDate;
    private String lessorFirstName;
    private String lessorLastName;
    private String lessorPhoneNumber;
    private String tenantFirstName;
    private String tenantLastName;
    private String tenantPhoneNumber;
    private String houseId;
    private String houseName;
    private String roomId;
    private String roomName;
    private Long contractPrice;

    public ContractSearchResDTO(Object[] x) {
        AtomicInteger i = new AtomicInteger(0);
        this.contractId = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.contractCode = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.contractStatusCode = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.contractStatusName = ContractStatusEnum.from(contractStatusCode).getTitle();
        this.createdDate = DateUtils.toStr(RepositoryUtils.setValueForField(LocalDate.class, x[i.getAndIncrement()]), DateUtils.F_DDMMYYYY);
        this.lessorFirstName = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.lessorLastName = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.lessorPhoneNumber = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.tenantFirstName = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.tenantLastName = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.tenantPhoneNumber = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.houseId = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.houseName = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.roomId = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.roomName = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.contractPrice = RepositoryUtils.setValueForField(Long.class, x[i.getAndIncrement()]);
    }
}
