package com.example.rentalservice.model.search.req;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BillSearchReqDTO {
    private String status;
    private Integer month;
    private Integer year;
    private Integer page;
    private Integer size;
}
