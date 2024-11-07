package com.example.rentalservice.model.search.req;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookSearchReqDTO {
    private String date;
    private String status;
    private Integer page;
    private Integer size;
}
