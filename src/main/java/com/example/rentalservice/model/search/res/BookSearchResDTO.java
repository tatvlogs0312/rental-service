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
public class BookSearchResDTO {
    private String bookId;
    private String roomId;
    private String roomCode;
    private String bookStatus;
    private String dateWatch;
    private String bookingMessage;
    private String lessorFirstName;
    private String lessorLastName;
    private String lessorPhoneNumber;
    private String tenantFirstName;
    private String tenantLastName;
    private String tenantPhoneNumber;
    private String position;
    private String ward;
    private String district;
    private String province;

    public BookSearchResDTO(Object[] x) {
        AtomicInteger i = new AtomicInteger(0);
        this.bookId = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.roomId = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.roomCode = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.bookStatus = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.dateWatch = DateUtils.toStr(RepositoryUtils.setValueForField(LocalDateTime.class, x[i.getAndIncrement()]), DateUtils.F_HHMMDDMMYYYY);
        this.bookingMessage = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.lessorFirstName = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.lessorLastName = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.lessorPhoneNumber = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.tenantFirstName = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.tenantLastName = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.tenantPhoneNumber = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.position = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.ward = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.district = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
        this.province = RepositoryUtils.setValueForField(String.class, x[i.getAndIncrement()]);
    }
}
